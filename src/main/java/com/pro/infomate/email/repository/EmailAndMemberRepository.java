package com.pro.infomate.email.repository;

import com.pro.infomate.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAndMemberRepository extends JpaRepository<Email, Integer> {
}
