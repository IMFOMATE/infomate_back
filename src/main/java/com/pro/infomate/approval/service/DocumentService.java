package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.dto.request.*;
import com.pro.infomate.approval.dto.response.*;
import com.pro.infomate.approval.entity.*;
import com.pro.infomate.approval.repository.ApprovalRepository;
import com.pro.infomate.approval.repository.DocRefRepository;
import com.pro.infomate.approval.repository.DocumentRepository;
import com.pro.infomate.approval.repository.PaymentListRepository;
import com.pro.infomate.approval.service.visitor.DocumentToDTOVisitor;
import com.pro.infomate.approval.dto.response.VacationResponse;
import com.pro.infomate.exception.NotEnoughDateException;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentService {

  private final DocumentRepository<Document> documentRepository;

  private final DocumentRepository<Vacation> vacationDocumentRepository;
  private final DocumentRepository<Payment> paymentDocumentRepository;
  private final DocumentRepository<Draft> draftDocumentRepository;

  private final MemberRepository memberRepository;

  private final ApprovalRepository approvalRepository;
  private final DocRefRepository docRefRepository;

  private final PaymentListRepository paymentListRepository;

  private final DocumentToDTOVisitor visitor;

  private final ModelMapper modelMapper;


  //1. 휴가문서 등록
//  @Transactional
//  public VacationResponse vacationSave(
//          int memberCode,
//          VacationRequest vacationRequest, String temp){
//
//    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));
//
//    if(member.getMemberOff() < 0 ){ //조건하나 더 넣어주기 신청한 일수랑 남은 휴가수 빼서!
//      throw new NotEnoughDateException("휴가일수가 부족합니다.");
//    }
//
//    Vacation vacation = modelMapper.map(vacationRequest, Vacation.class);
//    vacation.addMember(member);
//
//    if(temp != null){
//      vacation.setDocumentStatus(DocumentStatus.TEMPORARY);
//    }
//
//    Vacation save = vacationDocumentRepository.save(vacation);
//
//    System.out.println("vacation = " + vacation.getId());
//
//    // 참조자
//    if(vacationRequest.getRefList().size() > 0){
//      List<Integer> result = vacationRequest.getRefList().stream().map(RefRequest::getId).collect(Collectors.toList());
//
//      List<Member> memberList = memberRepository.findByMemberCodeIn(result);
//
//      memberList.forEach(m->{
//        DocRef ref = DocRef.builder().document(save).member(m).build();
//        docRefRepository.save(ref);
//      });
//    }
//
//    // 기안 리스트
//    vacationRequest.getApprovalList().forEach(list -> {
//      Member byMemberId = memberRepository.findByMemberCode(list.getId());
//      Approval approval = Approval.builder().order(list.getOrder()).member(byMemberId).document(save).build();
//
//      approvalRepository.save(approval);
//    });
//
//    return modelMapper.map(save, VacationResponse.class);
//
//  }

  //2. 기안서 등록
  @Transactional
  public DraftResponse draftSave(int memberCode, DraftRequest draftRequest, String temp){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Draft draft = modelMapper.map(draftRequest, Draft.class);
    draft.addMember(member);

    if(temp != null){
      draft.setDocumentStatus(DocumentStatus.TEMPORARY);
    }

    Draft save = draftDocumentRepository.save(draft);

    // 참조자
    if(draftRequest.getRefList().size() > 0){
      List<Integer> result = draftRequest.getRefList().stream().map(RefRequest::getId).collect(Collectors.toList());

      List<Member> memberList = memberRepository.findByMemberCodeIn(result);

      memberList.forEach(m->{
        DocRef ref = DocRef.builder().document(save).member(m).build();
        docRefRepository.save(ref);
      });
    }

    // 기안 리스트
    draftRequest.getApprovalList().forEach(list -> {
      Member byMemberId = memberRepository.findByMemberCode(list.getId());
      Approval approval = Approval.builder().order(list.getOrder()).member(byMemberId).document(save).build();

      approvalRepository.save(approval);
    });

    return modelMapper.map(save, DraftResponse.class);

  }

  //3. 지출결의서 등록
//  @Transactional
//  public PaymentResponse paymentSave(int memberCode, PaymentRequest paymentRequest, String temp){
//
//    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));
//
//    Payment payment = modelMapper.map(paymentRequest, Payment.class);
//    payment.addMember(member);
//
//    if(temp != null){
//      payment.setDocumentStatus(DocumentStatus.TEMPORARY);
//    }
//
//    Payment save = paymentDocumentRepository.save(payment);
//
//    // 참조자
//    if(paymentRequest.getRefList().size() > 0){
//      List<Integer> result = paymentRequest.getRefList().stream().map(RefRequest::getId).collect(Collectors.toList());
//
//      List<Member> memberList = memberRepository.findByMemberCodeIn(result);
//      memberList.forEach(m->{
//        DocRef ref = DocRef.builder().document(save).member(m).build();
//        docRefRepository.save(ref);
//      });
//    }
//
//  // 기안 리스트
//    paymentRequest.getApprovalList().forEach(list -> {
//      Member byMemberId = memberRepository.findByMemberCode(list.getId());
//      Approval approval = Approval.builder().order(list.getOrder()).member(byMemberId).document(save).build();
//
//      approvalRepository.save(approval);
//    });
//
//    //지출 리스트 저장
//    paymentRequest.getPaymentList().forEach(list -> {
//
//      PaymentList paymentList = PaymentList.builder()
//              .paymentContent(list.getPaymentContent())
//              .paymentDate(list.getPaymentDate())
//              .paymentPrice(list.getPaymentPrice())
//              .paymentSort(list.getPaymentSort())
//              .remarks(list.getRemarks())
//              .document(save)
//              .build();
//      paymentListRepository.save(paymentList);
//    });
//
//    return modelMapper.map(save, PaymentResponse.class);
//  }


  //4. 문서 세부
  @Transactional
  public DocumentDetailResponse findById(long documentId) {

    Document document = documentRepository.findById(documentId)
            .orElseThrow(() -> new NotFindDataException("해당문서가 없습니다."));

    return document.accept(visitor);
  }


  //5. 문서 리스트 top 5개
  public ApprovalHomeResponse top5List(int memberCode){
    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));
    //일단...
    PageRequest pageRequest = PageRequest.of(0, 5);

    //내 기안문서
    List<Document> approvalList = documentRepository.findTop5ByMemberOrderByCreatedDateDesc(member);

    //내 참조문서
    Page<DocumentListResponse> refList = docRefRepository.refPagingList(null, memberCode, pageRequest);

    //내 결재 대기문서
    List<Document> creditList = documentRepository.findCredit(memberCode);

    return ApprovalHomeResponse
            .builder()
            .approvalList(approvalList.stream().map(DocumentListResponse::new).collect(Collectors.toList()))
            .refList(refList.getContent())
            .creditList(creditList.stream().map(DocumentListResponse::new).collect(Collectors.toList()))
            .build();
  }


  //6. 기안문서 페이징
  public Page<DocumentListResponse> approvalList(String status, int memberCode, Pageable pageable){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    return documentRepository.findAllApproval(status, memberCode, pageable);

  }

  //7. 결재 대기 페이징
  public Page<DocumentListResponse> creditList(int memberCode, Pageable pageable){

    Page<Document> documents = documentRepository.findCreditWithPaging(memberCode, pageable);

    List<DocumentListResponse> content = documents.getContent().stream().map(DocumentListResponse::new).collect(Collectors.toList());

    return new PageImpl<>(content, pageable, content.size());

  }


  // 임시저장문서
  @Transactional
  public void update(DocumentRequest documentRequest){

  }

  // 문서삭제
  @Transactional
  public void deleteDocument(long documentId){
    documentRepository.deleteById(documentId);
  }



  private DocumentDetailResponse mapDocumentToDTO(Document document) {
    if (document instanceof Vacation) {
      return modelMapper.map((Vacation) document, VacationResponse.class);
    } else if (document instanceof Payment) {
      return modelMapper.map((Payment) document, PaymentResponse.class);
    } else if (document instanceof Draft) {
      return modelMapper.map((Draft) document, DraftResponse.class);
      // 다른 문서 유형에 대한 처리 추가 가능
    } else {
      throw new IllegalArgumentException("지원하지 않는 문서 유형입니다.");
    }
  }



}




