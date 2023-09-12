package com.pro.infomate.email.repository;

import com.pro.infomate.email.entity.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmailAndMemberRepository extends JpaRepository<Email, Integer> {


    @Query("SELECT c FROM Email c join c.member m where m.memberCode = :memberCode")
    List<Email> findByMemberCode(Integer memberCode);



}
