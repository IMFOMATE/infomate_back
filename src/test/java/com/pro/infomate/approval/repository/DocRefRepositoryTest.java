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
  @DisplayName("문서참조")
  void test() {
    Document document = documentRepository.findById(28L).orElseThrow();
    List<DocRef> refList = document.getRefList();
//    refList.forEach(l -> l.getMember());
    List<DocRef> result = docRefRepository.findByDocument(document);
//

  }

  @Test
  @DisplayName("top5")
  void top5() {
    Member member = memberRepository.findById(22).orElseThrow();
    List<DocRef> result = docRefRepository.findTop5ByMemberOrderByDocumentDesc(member);

    result.forEach(docRef -> System.out.println("docRef.getDocument() = " + docRef.getDocument().getId()));

  }
  
  
  @Test
  @DisplayName("testquery")
  void paging() {
    int memberCode =2;

    PageRequest pageRequest = PageRequest.of(0, 5);

    Page<DocumentListResponse> documentListResponses = docRefRepository.refPagingList(null,memberCode, pageRequest);
    System.out.println("Total = " + documentListResponses.getTotalPages());
    System.out.println("documentListResponses.getTotalElements() = " + documentListResponses.getTotalElements());
     documentListResponses.getContent().forEach(System.out::println);
    System.out.println("documentListResponses = " + documentListResponses.getSize());


  }

}