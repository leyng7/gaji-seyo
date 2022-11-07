package com.gajiseyo.modules.file.domain;

import com.gajiseyo.modules.common.BaseTimeEntity;
import com.gajiseyo.modules.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "TB_FILE")
@Entity
@Getter
@IdClass(ProgFile.FilePK.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgFile extends BaseTimeEntity implements Serializable {



    @Id
    @Column(name = "file_id")
    private String id;              // 파일 아이디

    @Id
    private int seq;                // 순서

    private String folderName;      // 폴더(프로그램) 이름

    private String orgFileName;     // 기존 파일 이름

    private String sysFileName;     // 저장 파일 이름

    private String dirPath;         // 저장 경로

    private String extension;       // 확장자

    private long fileSize;          // 파일 크기

    private long views;

    private boolean removed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id")
    private Member createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_up_id")
    private Member lastUpdatedBy;

    private ProgFile(String id, int seq, String folderName, String orgFileName, String sysFileName, String dirPath, String extension, Long fileSize, Member createdBy) {
        this.id = id;
        this.seq = seq;
        this.folderName = folderName;
        this.orgFileName = orgFileName;
        this.sysFileName = sysFileName;
        this.dirPath = dirPath;
        this.extension = extension;
        this.fileSize = fileSize;
        this.createdBy = createdBy;
        this.lastUpdatedBy = createdBy;
    }

    public static ProgFile create(String id, int seq, String folderName, String orgFileName, String sysFileName, String dirPath, String extension, long fileSize, Member createdBy) {
        return new ProgFile(
                id,
                seq,
                folderName,
                orgFileName.replace(" ", "_"),
                sysFileName,
                dirPath.replace(java.io.File.separatorChar, '/'),
                extension,
                fileSize,
                createdBy
        );
    }

    public String getDisplayFileSize() {
        return FileUtils.byteCountToDisplaySize(fileSize);
    }

    public String getThumbnailSrc() {
        return "/thumbnails/" + dirPath + "300_" + sysFileName;
    }


    protected static class FilePK implements Serializable {
        private String id;
        private int seq;
    }

}






























