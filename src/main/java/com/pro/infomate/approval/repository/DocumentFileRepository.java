package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentFileRepository extends JpaRepository<DocumentFile, Long> {

    // 1. 첨부파일 저장

    // 2. 첨부파일 조회

    // 3. 파일 다운로드

}
