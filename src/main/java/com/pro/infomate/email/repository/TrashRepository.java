package com.pro.infomate.email.repository;

import com.pro.infomate.email.entity.Trash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrashRepository extends JpaRepository<Trash, Integer> {

    void deleteByMailMailCode(Integer mailCode);
}
