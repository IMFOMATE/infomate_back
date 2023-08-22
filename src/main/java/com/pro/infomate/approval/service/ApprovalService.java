package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.repository.ApprovalRepository;
import com.pro.infomate.approval.repository.DocumentRepository;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalService {

  private final ApprovalRepository approvalRepository;
  private final MemberRepository memberRepository;
  private final DocumentRepository<Document> documentRepository;

  //결재 대기 문서 조회
  public DocumentListResponse ApprovalDocumentList(int memberCode){

      //회원의 결재대기문서 중 date값이 null인 문서만 가져와서
     // 해당하는 문서의 결재리스트를 가지고와서 순서대로 있을 때
    // 1번인데 date가 null 이거나
    // 1번이상의 수 본인의 date가 null 이면서 앞의 번호의 date가 null이 아니면

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    List<Approval> approvalList = approvalRepository.findByMemberAndApprovalDateIsNull(member);
    
  }

  //2. 결재 승인
  //해당문서의 결재리스트에서 order가 1인데 null이거나
  //order



  //3. 결재 반려

  //4. 결재 취소

  //5. 결재 리스트 저장

}
