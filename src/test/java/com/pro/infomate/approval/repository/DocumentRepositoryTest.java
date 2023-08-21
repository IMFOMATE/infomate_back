package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class DocumentRepositoryTest {

  @Autowired
  private DocumentRepository<Document> documentRepository;

  @Autowired
  private MemberRepository memberRepository;


  @Test
  @DisplayName("top")
  void top5() {

    int memberCode = 2;
    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    List<Document> result = documentRepository.findTop5ByMemberOrderByCreatedDateDesc(member);

    result.forEach(r -> System.out.println("result.getTitle() = " + r.getTitle()+r.getCreatedDate()));

  }

  @Test
  @DisplayName("approval")
  void approval() {
    int memberCode = 2;
    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));
    PageRequest page = PageRequest.of(0, 20, Sort.by("createdDate").descending());

    Page<Document> result = documentRepository.findByMember(member, page);
    
    result.forEach(r -> {
      System.out.println("r.getCreatedDate() = " + r.getCreatedDate());
      assertThat(r.getMember()).isEqualTo(member);
    });
  }
  
  @Test
  @DisplayName("dept")
  void deptList() {
    int memberCode = 2;
//    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));
    PageRequest page = PageRequest.of(0, 20, Sort.by("createdDate").descending());

    Page<DocumentListResponse> result = documentRepository.findByDeptDoc(memberCode, page);

    assertThat(result.getContent().size()).isEqualTo(0);

  }

}