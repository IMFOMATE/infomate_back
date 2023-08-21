package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.entity.DocRef;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class DocRefRepositoryTest {

  @Autowired
  private DocRefRepository docRefRepository;

  @Autowired
  private DocumentRepository<Document> documentRepository;

  @Autowired
  private MemberRepository memberRepository;


  @Test
  @DisplayName("test")
  void test() {
    Document document = documentRepository.findById(28L).orElseThrow();
    List<DocRef> refList = document.getRefList();
//    refList.forEach(l -> l.getMember());
    List<DocRef> result = docRefRepository.findByDocument(document);
//
    result.forEach(rf -> {
      int memberCode = rf.getMember().getMemberCode();

      Member ss = memberRepository.findByMemberCode(memberCode);
      ss.getRank().getRankName();
    });

  }
  
  
  @Test
  @DisplayName("testquery")
  void paging() {
    int memberCode =22;

    PageRequest pageRequest = PageRequest.of(0, 5);

    Page<DocumentListResponse> documentListResponses = docRefRepository.refPagingList(memberCode, pageRequest);
    System.out.println("documentListResponses = " + documentListResponses.getTotalPages());
     documentListResponses.getContent().forEach(System.out::println);
    System.out.println("documentListResponses = " + documentListResponses.getSize());


  }

}