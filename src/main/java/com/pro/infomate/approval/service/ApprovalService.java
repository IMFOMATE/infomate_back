package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.request.DocumentRequest;
import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.entity.Approval;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.repository.ApprovalRepository;
import com.pro.infomate.approval.repository.DocumentRepository;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalService {

  private final ApprovalRepository approvalRepository;
  private final MemberRepository memberRepository;
  private final DocumentRepository<Document> documentRepository;
  private final ModelMapper modelMapper;




  //결재 대기 문서 조회
  public List<DocumentListResponse> ApprovalDocumentList(int memberCode){

      //회원의 결재대기문서 중 date값이 null인 문서만 가져와서
     // 해당하는 문서의 결재리스트를 순서대로 가지고 오면서
    //date가 null인 첫번째껄 가지고 와서 그 member와 내가 같을때만 보여주기

    List<Document> documents = documentRepository.findApprovalsDocument(memberCode);

    List<Document> approvedDocuments = documents.stream()
            .map(approvalRepository::findTopByDocumentAndApprovalDateIsNullOrderByOrderAsc)
            .filter(approval -> approval != null && approval.getMember().getMemberCode() == memberCode)
            .map(Approval::getDocument)
            .collect(Collectors.toList());

    return approvedDocuments.stream().map((element) -> modelMapper.map(element, DocumentListResponse.class)).collect(Collectors.toList());

  }

  //2. 결재 승인
  //해당문서의 결재리스트에서 order가 1인데 null이거나
  //order

  public void approve(long documentCode){
    Document document = documentRepository.findById(documentCode).orElseThrow(() -> new NotFindDataException("요청하신 문서를 찾을 수 없습니다"));

//    approvalRepository.findByMem
  }



  //3. 결재 반려

  //4. 결재 취소

  //5. 결재 리스트 저장



}
