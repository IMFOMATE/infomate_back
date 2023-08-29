package com.pro.infomate.addressbook.repository;

import com.pro.infomate.addressbook.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {


    @Query("SELECT c FROM Contact c join c.member m where m.memberCode = :memberCode")
    List<Contact> findByMemberCode(Long memberCode);
}
