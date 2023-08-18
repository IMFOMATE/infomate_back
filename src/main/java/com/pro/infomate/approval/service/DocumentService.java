package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.*;
import com.pro.infomate.approval.dto.request.DraftRequest;
import com.pro.infomate.approval.dto.request.PaymentRequest;
import com.pro.infomate.approval.dto.request.VacationRequest;
import com.pro.infomate.approval.dto.response.*;
import com.pro.infomate.approval.entity.*;
import com.pro.infomate.approval.repository.DocumentRepository;
import com.pro.infomate.approval.service.visitor.DocumentToDTOVisitor;
import com.pro.infomate.exception.NotEnoughDateException;
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
public class DocumentService {

  private final DocumentRepository<Document> documentRepository;

  private final DocumentRepository<Vacation> vacationDocumentRepository;

  private final DocumentRepository<Payment> paymentDocumentRepository;

  private final DocumentRepository<Draft> draftDocumentRepository;

  private final MemberRepository memberRepository;

  private final DocumentToDTOVisitor visitor;

  private final ModelMapper modelMapper;



  //1. 휴가문서 등록
  @Transactional
  public VacationResponse vacationSave(long memberCode, VacationRequest vacationRequest){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    if(member.getMemberOff() < 0 ){ //조건하나 더 넣어주기 신청한 일수랑 남은 휴가수 빼서!
      throw new NotEnoughDateException("휴가일수가 부족합니다.");
    }

    Vacation vacation = modelMapper.map(vacationRequest, Vacation.class);
    vacation.addMember(member);
    Vacation save = vacationDocumentRepository.save(vacation);

    // 참조자
    if(vacationRequest.getRefList().size() > 0){
      List<Member> memberList = memberRepository.findByMemberCodeIn(vacationRequest.getRefList());
      List<DocRef> refList = memberList.stream().map(m -> DocRef.builder().document(save).member(m).build()).collect(Collectors.toList());
      save.setRefList(refList);
    }

    // 기안 리스트


    return modelMapper.map(save, VacationResponse.class);

  }

  //2. 기안서 등록
  @Transactional
  public DraftResponse draftSave(long memberCode, DraftRequest draftRequest){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Draft draft = modelMapper.map(draftRequest, Draft.class);

    Draft save = draftDocumentRepository.save(draft);

    // 참조자
    if(draftRequest.getRefList().size() > 0){
      List<Member> memberList = memberRepository.findByMemberCodeIn(draftRequest.getRefList());
      List<DocRef> refList = memberList.stream().map(m -> DocRef.builder().document(save).member(m).build()).collect(Collectors.toList());
      save.setRefList(refList);
    }

    // 기안 리스트



    return modelMapper.map(save, DraftResponse.class);

  }

  //3. 지출결의서 등록
  @Transactional
  public PaymentResponse paymentSave(long memberCode, PaymentRequest paymentRequest){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Payment payment = modelMapper.map(paymentRequest, Payment.class);

    Payment save = paymentDocumentRepository.save(payment);

    // 참조자
    if(paymentRequest.getRefList().size() > 0){
      List<Member> memberList = memberRepository.findByMemberCodeIn(paymentRequest.getRefList());
      List<DocRef> refList = memberList.stream().map(m -> DocRef.builder().document(save).member(m).build()).collect(Collectors.toList());
      save.setRefList(refList);
    }

    // 기안 리스트

    return modelMapper.map(save, PaymentResponse.class);

  }


  //4. 문서 세부
  public DocumentDetailResponse findById(long documentId) {

//    Document document = documentRepository.findById(documentId).orElseThrow(() -> new NotFindDataException("해당문서가 없습니다."));
//    return mapDocumentToDTO(document);

    Document document = documentRepository.findById(documentId)
            .orElseThrow(() -> new NotFindDataException("해당문서가 없습니다."));

    return document.accept(visitor);
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




