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
import org.springframework.data.domain.Pageable;
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

  @Test
  @DisplayName("querdsl")
  void findPendingApprovalsForMember() {
    List<Document> result = documentRepository.findApprovalsDocument(2);

    assertThat(result.size()).isEqualTo(2);
//    assertThat(result.get(0).getId()).isEqualTo(56);
  }

  @Test
  @DisplayName("select")
  void select() {
    Document document = documentRepository.findById(56L).orElseThrow();

    document.getApprovalList().forEach(d-> System.out.println("d.getId() = " + d.getId()));
  }
  
  @Test
  @DisplayName("findAllApproval")
  void findAllApproval() {
    PageRequest of = PageRequest.of(0, 10);

    Page<DocumentListResponse> allApproval = documentRepository.findAllApproval(null, 2, of);

    allApproval.forEach(a -> System.out.println("a.getId() = " + a.getId()));

  }

}