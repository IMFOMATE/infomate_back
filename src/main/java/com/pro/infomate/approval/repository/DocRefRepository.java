package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.DocRef;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.entity.DocumentFile;
import com.pro.infomate.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocRefRepository extends JpaRepository<DocRef,Long> {

  @EntityGraph(attributePaths = {"document"})
  List<DocRef> findByMember(Member member);

  @EntityGraph(attributePaths = {"member"})
  List<DocRef> findByDocument(Document document);

}
