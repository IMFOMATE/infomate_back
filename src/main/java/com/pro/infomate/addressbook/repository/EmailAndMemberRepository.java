package com.pro.infomate.addressbook.repository;

import com.pro.infomate.addressbook.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAndMemberRepository extends JpaRepository<Email, Integer> {
}
