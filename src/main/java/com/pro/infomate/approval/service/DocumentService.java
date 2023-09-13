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

  //1. 문서 저장
  @Transactional
  public <T extends DocumentRequest, R extends DocumentDetailResponse> R saveDocument(
          int memberCode, T documentRequest, List<MultipartFile> multipartFiles, Class<? extends Document> documentClass, Class<R> responseClass) {

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Document document = createDocument(member, documentRequest, documentClass);
    Document save = documentRepository.save(document);

    createRefer(documentRequest, save);
    createApprovals(documentRequest, member, save, memberCode,false);
    processFiles(multipartFiles, save);

    if (documentClass == Payment.class) {
      createPaymentList(((PaymentRequest) documentRequest).getPaymentList(), save);
    }

    return modelMapper.map(save, responseClass);
  }

  //2. 문서 세부
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

    boolean isRemove = document.getMember().equals(nowMember) &&
            (allApprovalDatesNull || approvalList.stream().anyMatch(app ->  ApprovalStatus.REJECT.equals(app.getApprovalStatus())));

    DocumentCondition condition = DocumentCondition.builder()
            .isDept(document.getMember().getDepartment().equals(nowMember.getDepartment()))
            .isCredit(approval != null && approval.getMember().getMemberCode() == memberCode)
            .isRemove(isRemove)
            .isCancel(document.getMember().equals(nowMember) && allApprovalDatesNull)
            .build();

    DocumentDetailResponse result = document.accept(visitor);
    result.setCondition(condition);

    return result;

  }

  //3. 문서 리스트 top 5개 main
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

  //4. 기안문서 페이징
  public Page<DocumentListResponse> approvalList(String status, int memberCode, Pageable pageable){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    return documentRepository.findAllApproval(status, memberCode, pageable);

  }

  //5. 결재 대기 페이징
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

  // 메인대시보드용
  public MainCreditResponse mainCredit(int memberCode){
    List<Document> documents = documentRepository.findApprovalsDocument(memberCode);

    int approvalCount = documentRepository.findByMemberDocuments(memberCode);


    int countBeforeLimit = (int)documents.stream()
            .map(approvalRepository::findTopByDocumentAndApprovalDateIsNullOrderByOrderAsc)
            .filter(approval -> approval != null && approval.getMember().getMemberCode() == memberCode)
            .map(Approval::getDocument)
            .filter(document -> document.getDocumentStatus() == DocumentStatus.WAITING)
            .count();

    List<Document> approvedDocuments = documents.stream()
            .map(approvalRepository::findTopByDocumentAndApprovalDateIsNullOrderByOrderAsc)
            .filter(approval -> approval != null && approval.getMember().getMemberCode() == memberCode)
            .map(Approval::getDocument)
            .filter(document -> document.getDocumentStatus() == DocumentStatus.WAITING )
            .limit(2)
            .collect(Collectors.toList());

    List<DocumentListResponse> creditList = approvedDocuments.stream().map(DocumentListResponse::new).collect(Collectors.toList());

    MainCreditResponse mainResp = MainCreditResponse.builder()
            .approvalCount(approvalCount)
            .doneList(0)
            .creditCount(countBeforeLimit)
            .creditList(creditList)
            .build();

    return mainResp;
  }


  //6. 부서문서
  public Page<DocumentListResponse> deptList(int memberCode, Pageable pageable) {

    Page<DocumentListResponse> deptResult = documentRepository.findByDeptDoc(memberCode, pageable);

    return new PageImpl<>(deptResult.getContent(), pageable, deptResult.getTotalElements());
  }

  //7. 문서삭제
  @Transactional
  public void deleteDocument(long documentId){

    documentRepository.findById(documentId).orElseThrow(() -> new NotFindDataException("해당문서가 없습니다"));
    documentRepository.deleteById(documentId);
  }

//  8. 결재 취소
  @Transactional
  public void cancelApproval(Long documentId, int memberCode){
    Document document = documentRepository.findById(documentId).orElseThrow(() -> new NotFindDataException("해당문서가 없습니다"));
    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));
    System.out.println("memberCode = " + memberCode);

    boolean allApprovalDatesNull = document.getApprovalList().stream()
            .allMatch(app -> app.getApprovalDate() == null);
    System.out.println("allApprovalDatesNull = " + allApprovalDatesNull);
    boolean isCancel = document.getMember().equals(member) && allApprovalDatesNull;
    System.out.println("isCancel = " + isCancel);
    //만약 해당조건이 아니면 바로 에러
    if(!isCancel) throw new NotEnoughDateException("결재를 취소 할 수 없습니다");

    //취소 가능하다면
    //1. 임시저장으로 바꾸자
    document.setDocumentStatus(DocumentStatus.TEMPORARY);

  }


  //문서 임시저장
  @Transactional
  public <T extends DocumentRequest, E extends Document> void tempSave(
          Long documentCode,
          int memberCode,
          T documentRequest,
          Class<E> entityClass,
          List<MultipartFile> multipartFiles,
          Boolean isSave
  ){
    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    // 1. 임시저장 시 documentCode가 없으면 저장되고
    // 값들을 받아서 저장하는데 문서상태는 temporary, 결재리스트는 waiting인 상태로

    if(documentCode == null){
      System.out.println("documentRequest = " + documentRequest);
      E document = createDocument(member, documentRequest, entityClass);
      document.setDocumentStatus(DocumentStatus.TEMPORARY);
      Document save = documentRepository.save(document);

      createRefer(documentRequest, save);
      createApprovals(documentRequest, member, save, memberCode,true);
      processFiles(multipartFiles, save);

      if (entityClass == Payment.class) {
        createPaymentList(((PaymentRequest) documentRequest).getPaymentList(), save);
      }

      return;
    }

    //2. 임시저장시 documentCode가 있으면 해당내용을 업데이트 시킨다.
    // 결재 리스트와 참조문서가 수정되었을 수 있기 때문에 approval, ref를 지우고 새롭게 들어온 값을 insert 하는 로직
    Document existingDocument = documentRepository.findById(documentCode)
            .orElseThrow(() -> new NotFindDataException("해당 문서는 존재하지 않습니다"));
    approvalRepository.deleteByDocument(existingDocument);
    docRefRepository.deleteByDocument(existingDocument);
    if (entityClass == Payment.class) {
      paymentListRepository.deleteByDocument(existingDocument);
    }

    existingDocument = updateDocument(existingDocument, documentRequest, entityClass, isSave, memberCode);
    createRefer(documentRequest, existingDocument);
    createApprovals(documentRequest, member, existingDocument, memberCode, true);
    processFiles(multipartFiles, existingDocument);

    if (entityClass == Payment.class) {
      createPaymentList(((PaymentRequest)documentRequest).getPaymentList(), existingDocument);
    }

  }

 //문서 만들기
  private <T extends DocumentRequest, E extends Document> E createDocument(Member member, T documentRequest, Class<E> entityClass) {

    E document = modelMapper.map(documentRequest, entityClass);

    document.addMember(member);
    document.setCreatedDate(LocalDateTime.now());
    return document;
  }

  //참조 저장
  private void createRefer(DocumentRequest documentRequest, Document save) {
    if(documentRequest.getRefList() == null) return;

    List<Integer> result = documentRequest.getRefList().stream().map(RefRequest::getId).collect(Collectors.toList());
    List<Member> memberList = memberRepository.findByMemberCodeIn(result);

    memberList.forEach(m -> {
      DocRef ref = DocRef.builder().document(save).member(m).build();
      docRefRepository.save(ref);
    });
  }

  // 결재 리스트 저장
  private void createApprovals(DocumentRequest documentRequest, Member member, Document save, int memberCode, boolean istemp) {
    if (save.getApprovalList() == null) {
      Approval approval = Approval.builder().order(1).member(member).document(save).build();
      approval.setApprovalStatus(istemp ? ApprovalStatus.WAITING : ApprovalStatus.APPROVAL);
      Approval savedApproval = approvalRepository.save(approval);

      savedApproval.setApprovalDate(save.getCreatedDate());
      save.setDocumentStatus(istemp ? DocumentStatus.TEMPORARY : DocumentStatus.APPROVAL);
    } else {
      List<Approval> approvals = documentRequest.getApprovalList()
              .stream()
              .map(list -> createApprovalFromInfo(list,member,save,memberCode))
              .collect(Collectors.toList());
      approvalRepository.saveAll(approvals);

      if (approvals.size() == 1 && approvals.get(0).getMember().getMemberCode() == memberCode && !istemp) {
        save.setDocumentStatus(DocumentStatus.APPROVAL);
      }
    }
  }

  // 파일 저장
  private void processFiles(List<MultipartFile> multipartFiles, Document save) {

    if(multipartFiles == null) return;

    List<DocFileResponse> files = null;
    try {
      files = FileUploadUtils.saveMultiFiles(FILES_DIR, multipartFiles);
      List<DocumentFile> fileList = files.stream().map(file -> new DocumentFile(file, save)).collect(Collectors.toList());
      documentFileRepository.saveAll(fileList);

    } catch (Exception e) {
      FileUploadUtils.deleteMultiFiles(FILES_DIR, files);
      throw new RuntimeException("파일업로드 실패");
    }
  }

  private void createPaymentList(List<PaymentListRequest> paymentList, Document save){
    List<PaymentList> paymentEntities = paymentList.stream()

            .map(paymentListRequest -> {
              PaymentList payment = PaymentList.builder()
                      .paymentDate(paymentListRequest.getPaymentDate())
                      .paymentSort(paymentListRequest.getPaymentSort())
                      .paymentPrice(paymentListRequest.getPaymentPrice())
                      .paymentContent(paymentListRequest.getPaymentContent())
                      .remarks(paymentListRequest.getRemarks().equals("null")? "" : paymentListRequest.getRemarks())
                      .document(save)
                      .build();
              return payment;
            })
            .collect(Collectors.toList());

    paymentListRepository.saveAll(paymentEntities);

  }

  //approval 매핑
  private Approval createApprovalFromInfo(ApprovalRequest approvalrequest, Member member, Document save, int memberCode) {
    Member byMemberId = memberRepository.findByMemberCode(approvalrequest.getId());
    ApprovalStatus approvalStatus = (byMemberId.getMemberCode() == memberCode) ? ApprovalStatus.APPROVAL : ApprovalStatus.WAITING;

    return Approval.builder()
            .order(approvalrequest.getOrder())
            .member(byMemberId)
            .document(save)
            .approvalStatus(approvalStatus)
            .approvalDate((approvalStatus == ApprovalStatus.APPROVAL) ? LocalDateTime.now() : null)
            .build();
  }


  // 문서 임시저장
  private <T extends DocumentRequest> Document updateDocument(Document existingDocument, T documentRequest, Class<?> entityClass,Boolean isSave, int memberCode) {

    if (isSave && existingDocument.getApprovalList().size() == 1
            && existingDocument.getApprovalList().get(0).getMember().getMemberCode() == memberCode) {
      existingDocument.setDocumentStatus(DocumentStatus.APPROVAL);
    } else {
      existingDocument.setDocumentStatus(isSave ? DocumentStatus.WAITING : DocumentStatus.TEMPORARY);
    }

    existingDocument.setCreatedDate(LocalDateTime.now());

    switch (entityClass.getSimpleName()) {
      case "Vacation":
          Vacation vacation = (Vacation) existingDocument;

          VacationRequest vacationRequest = (VacationRequest) documentRequest;

          vacation.setTitle(vacationRequest.getTitle());
          vacation.setContent(vacationRequest.getContent());
          vacation.setSort(vacationRequest.getSort());
          vacation.setStartDate(vacationRequest.getStartDate());
          vacation.setEndDate(vacationRequest.getEndDate());
          vacation.setEmergency(vacationRequest.getEmergency());
        break;
      case "Draft":
        Draft draft = (Draft) existingDocument;
        System.out.println("draft.getMember().getMemberCode() = " + draft.getMember().getMemberCode());

          DraftRequest draftRequest = (DraftRequest) documentRequest;

          draft.setTitle(draftRequest.getTitle());
          draft.setContent(draftRequest.getContent());
          draft.setStartDate(draftRequest.getStartDate());
          draft.setEmergency(draftRequest.getEmergency());
          draft.setCoDept(draftRequest.getCoDept().equals("null") ? "" : draftRequest.getCoDept());

        break;
      case "Payment":
        Payment payment = (Payment) existingDocument;
        PaymentRequest paymentRequest = (PaymentRequest) documentRequest;

        payment.setTitle(paymentRequest.getTitle());
        payment.setContent(paymentRequest.getContent());
        payment.setEmergency(paymentRequest.getEmergency());
        break;
      default:
        throw new IllegalArgumentException("Invalid entityClass");
    }

    return existingDocument;
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