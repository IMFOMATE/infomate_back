package com.pro.infomate.approval.entity;

import com.pro.infomate.approval.repository.DocumentRepository;
import com.pro.infomate.approval.repository.VacationRepository;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Commit
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

//    Draft draft = new Draft("경영지원");
//    draft.setTitle("협조부탁드립니다.");
//    draft.setCreatedDate(LocalDateTime.now());
//    draft.setContent("이러한 내용으로 협조 부탁드립니다.");
//    draft.setDocumentStatus(DocumentStatus.WAITING);
//    draft.setMember(member);
//
//    documentRepository.save(draft);

//    List<Document> documents = documentRepository.findByDocumentKindEquals("vacation");

    List<Document> documents = documentRepository.findAll();

    for (Document document : documents) {
      if(document instanceof Vacation){
        System.out.println((Vacation)document);
      }
      if(document instanceof Draft){
        System.out.println("((Draft) document).getCoDept() = " + ((Draft) document).getDocumentStatus());
      }
    }
    // When

    // Then

  }

}