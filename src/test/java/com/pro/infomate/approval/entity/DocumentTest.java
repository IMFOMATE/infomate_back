package com.pro.infomate.approval.entity;

import com.pro.infomate.approval.repository.DocumentRepository;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class DocumentTest {

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  DocumentRepository documentRepository;

  @Test
  @DisplayName("document test")
  void test1() {
    // Given
    Member member = memberRepository.findById(2L).orElseThrow();

    Vacation vacation = new Vacation("fff", LocalDateTime.now(),LocalDateTime.now(),"df");
    vacation.setTitle("ffs");
    vacation.setCreatedDate(LocalDateTime.now());
    vacation.setContent("fdff");
    vacation.setDocumentStatus(DocumentStatus.WAITING);
    vacation.setCoDept("asdf");
    vacation.setMember(member);

    documentRepository.save(vacation);

    // When

    // Then

  }

}