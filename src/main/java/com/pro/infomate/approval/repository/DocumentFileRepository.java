package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentFileRepository extends JpaRepository<DocumentFile, Long> {

  Optional<DocumentFile> findByFileName(String fileName);
}
