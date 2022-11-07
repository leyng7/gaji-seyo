package com.gajiseyo.modules.file.repository;

import com.gajiseyo.modules.file.domain.ProgFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<ProgFile, String> {

    ProgFile findByIdAndSeq(String id, int seq);

    List<ProgFile> findAllById(String id);

    @Modifying
    @Query("UPDATE ProgFile f SET f.removed = true WHERE f.id = :id AND f.seq = :seq")
    void remove(@Param("id") String id, @Param("seq") long seq);

    @Modifying
    @Query("UPDATE ProgFile f SET f.views = COALESCE(f.views, 0) + 1 WHERE f.id = :id AND f.seq = :seq")
    void incrementViews(@Param("id") String id, @Param("seq") long seq);

    @Query("SELECT COALESCE(MAX(f.seq), 0) FROM ProgFile f WHERE f.id = :id")
    int findMaxSeqById(@Param("id") String id);

}
