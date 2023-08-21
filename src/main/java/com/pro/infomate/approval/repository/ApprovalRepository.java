package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {

  //1. 결재 리스트 조회

  //2. 결재 승인

  //3. 결재 반려

  //4. 결재 취소

  //5. 결재 리스트 저장


}
