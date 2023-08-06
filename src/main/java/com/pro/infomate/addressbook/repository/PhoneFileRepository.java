package com.pro.infomate.addressbook.repository;

import com.pro.infomate.addressbook.entity.PhotoFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneFileRepository extends JpaRepository<PhotoFile, Integer> {
}
