package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.DocRef;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.entity.DocumentFile;
import com.pro.infomate.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;

public interface DocRefRepository extends JpaRepository<DocRef,Long>, DocRefRepositoryCustom{

  @EntityGraph(attributePaths = {"document"})
  List<DocRef> findTop5ByMemberOrderByDocumentDesc(Member member);

  @EntityGraph(attributePaths = {"member"})
  List<DocRef> findByDocument(Document document);

  void deleteByDocument(Document document);


}
