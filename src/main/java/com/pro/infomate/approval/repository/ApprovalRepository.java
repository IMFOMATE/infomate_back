package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<Approval, Long>, ApprovalRepositoryCustom {


  List<Approval> findByMemberAndApprovalDateIsNull(Member member);

  @EntityGraph(attributePaths = {"member"})
  Approval findTopByDocumentAndApprovalDateIsNullOrderByOrderAsc(Document document);


//  Approval findByApproval(long id);


}
