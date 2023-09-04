package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.dto.request.*;
import com.pro.infomate.approval.dto.response.*;
import com.pro.infomate.approval.entity.*;
import com.pro.infomate.approval.repository.*;
import com.pro.infomate.approval.service.visitor.DocumentToDTOVisitor;
import com.pro.infomate.approval.dto.response.VacationResponse;
import com.pro.infomate.exception.NotEnoughDateException;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import com.pro.infomate.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Comparator;
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
  private final DocumentFileRepository documentFileRepository;
  private final MemberRepository memberRepository;

  private final ApprovalRepository approvalRepository;
  private final DocRefRepository docRefRepository;

  private final PaymentListRepository paymentListRepository;

  private final DocumentToDTOVisitor visitor;

  private final ModelMapper modelMapper;

  @Value("${files.files-dir}")
  private String FILES_DIR;
  @Value("${files.files-url}")
  private String FILES_URL;

  //1. 휴가문서 등록
  @Transactional
  public VacationResponse vacationSave(
          int memberCode,
          VacationRequest vacationRequest, String temp, List<MultipartFile> multipartFiles){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    if(member.getMemberOff() < 0 ){ //조건하나 더 넣어주기 신청한 일수랑 남은 휴가수 빼서!
      throw new NotEnoughDateException("휴가일수가 부족합니다.");
    }

    Vacation vacation = modelMapper.map(vacationRequest, Vacation.class);
    vacation.addMember(member);
    vacation.setCreatedDate(LocalDateTime.now());

    if(temp != null){
      vacation.setDocumentStatus(DocumentStatus.TEMPORARY);
    }

    Vacation save = vacationDocumentRepository.save(vacation);

    System.out.println("vacation = " + vacation.getId());
    System.out.println("vacation = " + vacation.getCreatedDate());

    // 참조자
    if(vacationRequest.getRefList() != null){
      List<Integer> result = vacationRequest.getRefList().stream().map(RefRequest::getId).collect(Collectors.toList());

      List<Member> memberList = memberRepository.findByMemberCodeIn(result);

      memberList.forEach(m->{
        DocRef ref = DocRef.builder().document(save).member(m).build();
        docRefRepository.save(ref);
      });
    }
    // 기안 리스트
    if(vacationRequest.getApprovalList() == null){
      Approval approval = Approval.builder().order(1).member(member).document(save).build();
      Approval saveapproval = approvalRepository.save(approval);
      saveapproval.setApprovalDate(save.getCreatedDate());
      save.setDocumentStatus(DocumentStatus.APPROVAL);

    }else{
      vacationRequest.getApprovalList().forEach(list -> {
        Member byMemberId = memberRepository.findByMemberCode(list.getId());
        Approval approval = Approval.builder().order(list.getOrder()).member(byMemberId).document(save).build();

        approvalRepository.save(approval);
      });
    }

    if(multipartFiles != null){
      List<DocFileResponse> files = null;

      //파일 저장
      try{
        files = FileUploadUtils.saveMultiFiles(FILES_DIR, multipartFiles);

        List<DocumentFile> fileList = files.stream().map(file -> new DocumentFile(file, save)).collect(Collectors.toList());
        documentFileRepository.saveAll(fileList);

      }catch (Exception e){
        FileUploadUtils.deleteMultiFiles(FILES_DIR, files);
        throw new RuntimeException("파일업로드 실패");
      }
    }

    return modelMapper.map(save, VacationResponse.class);

  }

  //2. 기안서 등록
  @Transactional
  public DraftResponse draftSave(int memberCode, DraftRequest draftRequest, String temp, List<MultipartFile> multipartFiles){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Draft draft = modelMapper.map(draftRequest, Draft.class);
    draft.addMember(member);


    if(temp != null){
      draft.setDocumentStatus(DocumentStatus.TEMPORARY);
    }

    Draft save = draftDocumentRepository.save(draft);
    save.setCreatedDate(LocalDateTime.now());

    // 참조자
    if(draftRequest.getRefList() != null){
      List<Integer> result = draftRequest.getRefList().stream().map(RefRequest::getId).collect(Collectors.toList());

      List<Member> memberList = memberRepository.findByMemberCodeIn(result);

      memberList.forEach(m->{
        DocRef ref = DocRef.builder().document(save).member(m).build();
        docRefRepository.save(ref);
      });
    }

    // 기안 리스트
    if(draftRequest.getApprovalList() == null){
      Approval approval = Approval.builder().order(1).member(member).document(save).build();
      Approval saveapproval = approvalRepository.save(approval);
      saveapproval.setApprovalDate(save.getCreatedDate());
      save.setDocumentStatus(DocumentStatus.APPROVAL);

    }else{
      draftRequest.getApprovalList().forEach(list -> {
        Member byMemberId = memberRepository.findByMemberCode(list.getId());
        Approval approval = Approval.builder().order(list.getOrder()).member(byMemberId).document(save).build();

        approvalRepository.save(approval);
      });
    }

    if(multipartFiles != null){
      List<DocFileResponse> files = null;

      //파일 저장
      try{
        files = FileUploadUtils.saveMultiFiles(FILES_DIR, multipartFiles);

        List<DocumentFile> fileList = files.stream().map(file -> new DocumentFile(file, save)).collect(Collectors.toList());
        documentFileRepository.saveAll(fileList);

      }catch (Exception e){
        FileUploadUtils.deleteMultiFiles(FILES_DIR, files);
        throw new RuntimeException("파일업로드 실패");
      }
    }

    return modelMapper.map(save, DraftResponse.class);

  }

  //3. 지출결의서 등록
  @Transactional
  public PaymentResponse paymentSave(
          int memberCode,
          PaymentRequest paymentRequest, String temp, List<MultipartFile> multipartFiles){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));
    System.out.println("paymentRequest = " + paymentRequest.getPaymentList());
    Payment payment = modelMapper.map(paymentRequest, Payment.class);
    payment.addMember(member);
    payment.setCreatedDate(LocalDateTime.now());

    if(temp != null){
      payment.setDocumentStatus(DocumentStatus.TEMPORARY);
    }

    Payment save = paymentDocumentRepository.save(payment);

    // 참조자
    if(paymentRequest.getRefList() != null){
      List<Integer> result = paymentRequest.getRefList().stream().map(RefRequest::getId).collect(Collectors.toList());

      List<Member> memberList = memberRepository.findByMemberCodeIn(result);

      memberList.forEach(m->{
        DocRef ref = DocRef.builder().document(save).member(m).build();
        docRefRepository.save(ref);
      });
    }
    // 결재 리스트
    if(paymentRequest.getApprovalList() == null){
      Approval approval = Approval.builder().order(1).member(member).document(save).build();
      Approval saveapproval = approvalRepository.save(approval);
      saveapproval.setApprovalDate(save.getCreatedDate());
      save.setDocumentStatus(DocumentStatus.APPROVAL);

    }else{
      paymentRequest.getApprovalList().forEach(list -> {
        Member byMemberId = memberRepository.findByMemberCode(list.getId());
        Approval approval = Approval.builder().order(list.getOrder()).member(byMemberId).document(save).build();

        approvalRepository.save(approval);
      });
    }

    //지출 리스트 저장
    if(paymentRequest.getPaymentList() != null){
      paymentRequest.getPaymentList().forEach(list -> {

        PaymentList paymentList = new PaymentList(list.getPaymentDate(), list.getPaymentSort(), list.getPaymentPrice(), list.getPaymentContent(), list.getRemarks(), save);
        save.addPaymentList(paymentList);

        paymentListRepository.save(paymentList);
      });
    }

    //파일 저장
    if(multipartFiles != null){
      List<DocFileResponse> files = null;

      //파일 저장
      try{
        files = FileUploadUtils.saveMultiFiles(FILES_DIR, multipartFiles);

        List<DocumentFile> fileList = files.stream().map(file -> new DocumentFile(file, save)).collect(Collectors.toList());
        documentFileRepository.saveAll(fileList);

      }catch (Exception e){
        FileUploadUtils.deleteMultiFiles(FILES_DIR, files);
        throw new RuntimeException("파일업로드 실패");
      }
    }

    return modelMapper.map(save, PaymentResponse.class);
  }

  //4. 문서 세부
  @Transactional
  public DocumentDetailResponse findById(long documentId, int memberCode) {

    Member nowMember = memberRepository.findByMemberCode(memberCode);

    Document document = documentRepository.findById(documentId)
            .orElseThrow(() -> new NotFindDataException("해당문서가 없습니다."));

    // 결재여부
    List<Approval> approvalList = document.getApprovalList();
    approvalList.sort(Comparator.comparingInt(Approval::getOrder));

    Approval approval = approvalList.stream().filter(app -> app.getApprovalDate() == null)
            .findFirst().orElse(null);

    boolean allApprovalDatesNull = approvalList.stream()
            .allMatch(app -> app.getApprovalDate() == null);

    DocumentCondition condition = DocumentCondition.builder()
            .isDept(document.getMember().getDepartment().equals(nowMember.getDepartment()))
            .isCredit(approval != null && approval.getMember().getMemberCode() == memberCode)
            .isCancel(document.getMember().equals(nowMember) && allApprovalDatesNull)
            .build();

    System.out.println("condition = " + condition);

    // 생각해보자 문서 보여줘야하는건
    // insert 시 : 결재 요청, 임시저장, 취소, 결재선 선택
    // 내가 작성한 문서는 : 재기안, 삭제, 상신취소, 목록, 다운로드/미리보기
    // 결재해야하는 문서 : 결재, 반려, 목록, 다운로드/미리보기
    // 다른 사람 문서 또는 참조된문서 :  다운로드/미리보기, 목록
    // 우리부서 문서 : 재기안, 목록, 미리보기/다운로드

    // insert 제외하고 공통적인 버튼 : 목록, 다운로드/미리보기
    // 재기안이 되야되는 건 내가 작성한문서와 우리부서문서 => 재기안 누르면 데이터가지고 insert
    // 결재여부가 true 일때 결재 반려 보여주기
    // 재기안여부 true 일때 재기안버튼 보여주기
    // 상신취소는 내문서이면서 결재 한사람이 없을떄 ?

    // 결재 반려 재기안 삭제, 임시저장, 취소
    // 미리보기 또는 다운로드

    // 임시저장..........


    DocumentDetailResponse result = document.accept(visitor);
    result.setCondition(condition);

    return result;

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
    List<Document> documents = documentRepository.findApprovalsDocument(memberCode);

    List<Document> approvedDocuments = documents.stream()
            .map(approvalRepository::findTopByDocumentAndApprovalDateIsNullOrderByOrderAsc)
            .filter(approval -> approval != null && approval.getMember().getMemberCode() == memberCode)
            .map(Approval::getDocument)
            .filter(document -> document.getDocumentStatus() == DocumentStatus.WAITING )
            .limit(5)
            .collect(Collectors.toList());

    List<DocumentListResponse> creditList = approvedDocuments.stream().map(DocumentListResponse::new).collect(Collectors.toList());

    return ApprovalHomeResponse
            .builder()
            .approvalList(approvalList.stream().map(DocumentListResponse::new).collect(Collectors.toList()))
            .refList(refList.getContent())
            .creditList(creditList)
            .build();
  }

  //6. 기안문서 페이징
  public Page<DocumentListResponse> approvalList(String status, int memberCode, Pageable pageable){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    return documentRepository.findAllApproval(status, memberCode, pageable);

  }

  //7. 결재 대기 페이징
  public Page<DocumentListResponse> creditList(int memberCode, Pageable pageable) {
    List<Document> documents = documentRepository.findApprovalsDocument(memberCode);

    List<Document> approvedDocuments = documents.stream()
            .map(approvalRepository::findTopByDocumentAndApprovalDateIsNullOrderByOrderAsc)
            .filter(approval -> approval != null && approval.getMember().getMemberCode() == memberCode)
            .map(Approval::getDocument)
            .filter(document -> document.getDocumentStatus() == DocumentStatus.WAITING )
            .collect(Collectors.toList());

    Page<Document> creditPaging = new PageImpl<>(approvedDocuments, pageable, approvedDocuments.size());

    return creditPaging.map(DocumentListResponse::new);
  }

  // 부서문서
  public Page<DocumentListResponse> deptList(int memberCode, Pageable pageable) {

    Page<DocumentListResponse> deptResult = documentRepository.findByDeptDoc(memberCode, pageable);

    return new PageImpl<>(deptResult.getContent(), pageable, deptResult.getTotalElements());

  }



  // 문서삭제
  @Transactional
  public void deleteDocument(long documentId){

    documentRepository.findById(documentId).orElseThrow(() -> new NotFindDataException("해당문서가 없습니다"));
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




