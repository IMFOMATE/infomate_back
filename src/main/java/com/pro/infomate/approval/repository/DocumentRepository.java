package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository<T extends Document> extends JpaRepository<T, Long>, DocumentRepositoryCustom {

  // 1. 내 기안문서 top 5
  List<Document> findTop5ByMemberOrderByCreatedDateDesc(Member member);

  // 2. 내 기안문서 페이징
  Page<Document> findByMember(Member member, Pageable pageable);


  @Override
  @EntityGraph(attributePaths = {"member"})
  Optional<T> findById(Long aLong);


  @Query("select d from Document d where d.documentKind in (:type)")
  List<Document> findByDocuments(@Param("type") String type);



}
