package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.*;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.entity.Draft;
import com.pro.infomate.approval.entity.Payment;
import com.pro.infomate.approval.entity.Vacation;
import com.pro.infomate.approval.repository.DocumentRepository;
import com.pro.infomate.exception.NotEnoughDateException;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentService {

  private final DocumentRepository<Document> documentRepository;

  private final DocumentRepository<Vacation> vacationDocumentRepository;

  private final DocumentRepository<Payment> paymentDocumentRepository;

  private final DocumentRepository<Draft> draftDocumentRepository;

  private final MemberRepository memberRepository;

  private final ModelMapper modelMapper;


  
  //1. 휴가문서 등록
  @Transactional
  public VacationDTO vacationSave(long memberCode, VacationDTO vacationDTO){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    if(member.getMemberOff() < 0 ){ //조건하나 더 넣어주기 신청한 일수랑 남은 휴가수 빼서!
      throw new NotEnoughDateException("휴가일수가 부족합니다.");
    }

    Vacation vacation = modelMapper.map(vacationDTO, Vacation.class);
    vacation.addMember(member);

    // 기안리스트 insert 해야함.
    // 참조자 insert 해야함

    return modelMapper.map(vacationDocumentRepository.save(vacation), VacationDTO.class);

  }

  //2. 기안서 등록
  @Transactional
  public DraftDTO draftSave(long memberCode, DraftDTO draftDTO){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Draft draft = modelMapper.map(draftDTO, Draft.class);

    // 기안리스트 insert 해야함.
    // 참조자 insert 해야함

    return modelMapper.map(draftDocumentRepository.save(draft), DraftDTO.class);

  }

  //3. 지출결의서 등록
  @Transactional
  public PaymentDTO paymentSave(long memberCode, PaymentDTO paymentDTO){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Payment payment = modelMapper.map(paymentDTO, Payment.class);

    // 기안리스트 insert 해야함.
    // 참조자 insert 해야함

    return modelMapper.map(paymentDocumentRepository.save(payment), PaymentDTO.class);

  }



  //2. 문서 세부
  public DocumentDTO findById(long DocumentId) {

    Document document = documentRepository.findById(DocumentId).orElseThrow(() -> new NotFindDataException("해당문서가 없습니다."));
    return mapDocumentToDTO(document);
  }

  private DocumentDTO mapDocumentToDTO(Document document) {
    if (document instanceof Vacation) {
      return modelMapper.map((Vacation) document, VacationDTO.class);
    } else if (document instanceof Payment) {
      return modelMapper.map((Payment) document, PaymentDTO.class);
    } else if (document instanceof Draft) {
      return modelMapper.map((Draft) document, DraftDTO.class);
      // 다른 문서 유형에 대한 처리 추가 가능
    } else {
      throw new IllegalArgumentException("지원하지 않는 문서 유형입니다.");
    }
  }



}




