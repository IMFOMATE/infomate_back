package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.DocRef;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
      Long memberCode = rf.getMember().getMemberCode();

      Member ss = memberRepository.findByMemberCode(memberCode);
      ss.getRank().getRankName();
    });

  }

}