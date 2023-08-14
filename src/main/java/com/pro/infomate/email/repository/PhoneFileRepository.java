package com.pro.infomate.email.repository;

import com.pro.infomate.email.entity.PhotoFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneFileRepository extends JpaRepository<PhotoFile, Integer> {
}
