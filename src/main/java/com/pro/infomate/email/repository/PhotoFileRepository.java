package com.pro.infomate.email.repository;

import com.pro.infomate.email.entity.PhotoFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoFileRepository extends JpaRepository<PhotoFile, Integer> {

    List<PhotoFile> findByMailMailCode(Integer mailCode);
}
