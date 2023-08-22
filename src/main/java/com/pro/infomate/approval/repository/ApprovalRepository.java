package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {


  List<Approval> findByMemberAndApprovalDateIsNull(Member member);


}
