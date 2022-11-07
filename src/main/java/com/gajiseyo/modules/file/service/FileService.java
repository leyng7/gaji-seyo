package com.gajiseyo.modules.file.service;

import com.gajiseyo.modules.file.domain.ProgFile;
import com.gajiseyo.modules.file.repository.FileRepository;
import com.gajiseyo.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;
    private final Tika tika = new Tika();

    @Value("${UPLOAD_PATH}")
    private String UPLOAD_PATH;

    @Value("${THUMBNAIL_PATH}")
    private String THUMBNAIL_PATH;

    @Transactional
    public ProgFile uploadFile(String id, String folderName, MultipartFile file, Member createdBy) {
        if (StringUtils.isBlank(id)) id = UUID.randomUUID().toString();
        return saveFile(id, folderName, file, createdBy);
    }

    public String uploadFiles(String folderName, List<MultipartFile> files, Member createdBy) {
        String uuid = UUID.randomUUID().toString();

        for (MultipartFile file : files) {
            saveFile(uuid, folderName, file, createdBy);
        }

        return uuid;
    }

    public String uploadFiles(String id, String folderName, List<MultipartFile> files, Member createdBy) {
        for (MultipartFile file : files) {
            saveFile(id, folderName, file, createdBy);
        }

        return id;
    }


    private ProgFile saveFile(String id, String folderName, MultipartFile file, Member createdBy) {
        if (file == null || file.getSize() == 0) return null;

        UUID fromUUID = UUID.fromString(id);
        if (!fromUUID.toString().equals(id)) throw new RuntimeException("UUID 형식을 확인해주세요.");

        String orgFileName = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(orgFileName).toLowerCase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String sysFileName = simpleDateFormat.format(new Date()) + "." + extension;

        String folderPath = folderName + '/' + sysFileName.substring(0, 4) + '/';
        String dirPath = UPLOAD_PATH + folderPath;
        String uploadPath = dirPath + sysFileName;

        File dirPathFile = new File(dirPath);
        File uploadFile = new File(uploadPath);

        if (!dirPathFile.exists()) {
            boolean mkdirs = dirPathFile.mkdirs();
            if (!mkdirs) throw new RuntimeException("폴더 생성에 실패하였습니다.");
        }

        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 변환에 실패하였습니다.");
        }

        if (file.getContentType().startsWith("image")) {
            try {
                uploadThumbnailFile(uploadFile, folderPath, sysFileName, 300);
            } catch (IOException e) {
                log.debug("썸네일 생성에 실패하였습니다.");
            }
        }

        int maxSeq = fileRepository.findMaxSeqById(id);

        ProgFile progFile = ProgFile.create(
                id,
                ++maxSeq,
                folderName,
                orgFileName,
                sysFileName,
                folderPath,
                extension,
                file.getSize(),
                createdBy
        );

        fileRepository.save(progFile);

        return progFile;
    }

    public List<ProgFile> getFilesById(String id) {
        return fileRepository.findAllById(id);
    }

    public ProgFile getFileByIdAndSeq(String id, int fileSeq) {
        return fileRepository.findByIdAndSeq(id, fileSeq);
    }

    public ProgFile getFileById(String id) {
        return fileRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void removeByIdAndSeq(String id, long seq) {
        fileRepository.remove(id, seq);
    }

    public long totalFileSize(List<ProgFile> files) {
        long totalFileSize = 0;
        for (ProgFile file : files) {
            totalFileSize += file.getFileSize();
        }
        return totalFileSize;
    }

    public void downloadFile(HttpServletResponse request, HttpServletResponse response, ProgFile progFile) throws IOException {

        if (progFile == null) {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('해당하는 파일이 존재하지 않습니다.');history.go(-1);</script>");
            out.close();
            return;
        }

        File file = new File(UPLOAD_PATH + progFile.getDirPath() + progFile.getSysFileName());

        if (!file.exists()) {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('해당하는 파일이 존재하지 않습니다.');history.go(-1);</script>");
            out.close();
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + java.net.URLEncoder.encode(progFile.getOrgFileName(), StandardCharsets.UTF_8) + "\"");

        if (StringUtils.contains(userAgent, "MSIE")) {
            int i = userAgent.indexOf('M', 2);
            String IEV = userAgent.substring(i + 5, i + 8);
            if (IEV.equalsIgnoreCase("5.5")) {
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + java.net.URLEncoder.encode(progFile.getOrgFileName(), StandardCharsets.UTF_8) + "\"");
            }
        }

        FileInputStream fileInputStream = new FileInputStream(file); // 파일 읽어오기
        OutputStream outputStream = response.getOutputStream();

        FileCopyUtils.copy(fileInputStream, outputStream);
    }

    public void zipDownloadFiles(HttpServletResponse request, HttpServletResponse response, String id) throws IOException {
        List<ProgFile> files = getFilesById(id);

        if (files == null || files.isEmpty()) {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('해당되는 파일이 존재하지 않습니다.');history.go(-1);</script>");
            out.close();
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/zip");

        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + java.net.URLEncoder.encode(DateFormatUtils.format(new Date(), "yyyy-MM-dd_HHmmss") + ".zip", CharEncoding.UTF_8) + "\"");

        if (StringUtils.contains(userAgent, "MSIE")) {
            int i = userAgent.indexOf('M', 2);
            String IEV = userAgent.substring(i + 5, i + 8);
            if (IEV.equalsIgnoreCase("5.5")) {
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + java.net.URLEncoder.encode(DateFormatUtils.format(new Date(), "yyyy-MM-dd_HHmmss") + ".zip", CharEncoding.UTF_8) + "\"");
            }
        }


        int i = 1;
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            // DB에 저장되어 있는 파일 목록을 읽어온다.

            for (ProgFile progFile : files) {
                incrementViews(progFile.getId(), progFile.getSeq());

                File file = new File(UPLOAD_PATH + progFile.getDirPath() + progFile.getSysFileName());

                if (!file.exists()) continue;

                zipOut.putNextEntry(new ZipEntry(i + "_" + progFile.getOrgFileName()));

                try (FileInputStream fis = new FileInputStream(file)) {
                    StreamUtils.copy(fis, zipOut);
                }

                zipOut.closeEntry();
                i++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

/*    public void copyUploadThumbnails() {
        List<File> progFiles = fileRepository.findByExtensions("jpg", "jpeg", "png", "gif");

        for (File progFile : progFiles) {
            File file = new File(UPLOAD_PATH + progFile.getDirPath() + progFile.getSysFileName());
            if (!file.exists()) continue;
            try {
                uploadThumbnailFile(file, progFile.getDirPath(), progFile.getSysFileName(), 300);
            } catch (IOException e) {
                log.debug("썸네일 생성에 실패하였습니다.");
            }
        }

    }*/

    public String saveEditorImageFile(MultipartFile file) {
        String orgFileName = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(orgFileName);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String sysFileName = simpleDateFormat.format(new Date()) + "." + extension;

        String folderPath = "editor" + '/' + sysFileName.substring(0, 4) + '/' + sysFileName.substring(4, 6) + '/';
        String dirPath = THUMBNAIL_PATH + folderPath;
        String uploadPath = dirPath + sysFileName;

        File dirPathFile = new File(dirPath);
        File uploadFile = new File(uploadPath);

        if (!dirPathFile.exists()) {
            boolean mkdirs = dirPathFile.mkdirs();
            if (!mkdirs) throw new RuntimeException("폴더 생성에 실패하였습니다.");
        }

        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 변환에 실패하였습니다.");
        }

        return "/thumbnails/" + folderPath + sysFileName;
    }

    private void uploadThumbnailFile(InputStream inputStream, String folderPath, String sysFileName, Integer fileSize) throws IOException {
        String mimeType = tika.detect(inputStream);
        if (!mimeType.startsWith("image")) return;

        String thumbnailDirPath = THUMBNAIL_PATH + folderPath;
        String thumbnailPath = thumbnailDirPath + fileSize.toString() + "_" + sysFileName;

        File thumbnailDirPathFile = new File(thumbnailDirPath);
        File thumbnailUploadFile = new File(thumbnailPath);

        if (!thumbnailDirPathFile.exists()) {
            boolean mkdirs = thumbnailDirPathFile.mkdirs();
            if (!mkdirs) throw new IOException("썸네일 폴더 생성에 실패하였습니다.");
        }

        if (thumbnailUploadFile.exists()) return;

        Thumbnails.of(inputStream)
                .size(fileSize, fileSize)
                .outputQuality(0.8)
                .toFile(thumbnailUploadFile);

    }

    private void uploadThumbnailFile(File file, String folderPath, String sysFileName, Integer fileSize) throws IOException {
        String mimeType = tika.detect(file);
        if (!mimeType.startsWith("image")) return;

        String thumbnailDirPath = THUMBNAIL_PATH + folderPath;
        String thumbnailPath = thumbnailDirPath + fileSize.toString() + "_" + sysFileName;

        File thumbnailDirPathFile = new File(thumbnailDirPath);
        File thumbnailUploadFile = new File(thumbnailPath);

        if (!thumbnailDirPathFile.exists()) {
            boolean mkdirs = thumbnailDirPathFile.mkdirs();
            if (!mkdirs) throw new IOException("썸네일 폴더 생성에 실패하였습니다.");
        }

        if (thumbnailUploadFile.exists()) return;

        Thumbnails.of(file)
                .size(fileSize, fileSize)
                .outputQuality(0.8)
                .toFile(thumbnailUploadFile);

    }

    public void incrementViews(String id, long seq) {
        fileRepository.incrementViews(id, seq);
    }

}

