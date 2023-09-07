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
          VacationRequest vacationRequest, List<MultipartFile> multipartFiles){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    if(member.getMemberOff() < 0 ){ //조건하나 더 넣어주기 신청한 일수랑 남은 휴가수 빼서!
      throw new NotEnoughDateException("휴가일수가 부족합니다.");
    }

    Vacation vacation = modelMapper.map(vacationRequest, Vacation.class);
    vacation.addMember(member);
    vacation.setCreatedDate(LocalDateTime.now());

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

    // 기안리스트가 없을 때 -> 현재 로그인사용자가 결재승인 및 문서 종로
    if(vacation.getApprovalList() == null){
      Approval approval = Approval.builder().order(1).member(member).document(save).build();
      approval.setApprovalStatus(ApprovalStatus.APPROVAL);
      Approval saveapproval = approvalRepository.save(approval);
      saveapproval.setApprovalDate(save.getCreatedDate());
      save.setDocumentStatus(DocumentStatus.APPROVAL);
    }
    else{
    //기안리스트가 있을때
      List<Approval> approvals = vacationRequest.getApprovalList()
              .stream()
              .map(list -> {
                Member byMemberId = memberRepository.findByMemberCode(list.getId());
                Approval approval = Approval.builder()
                        .order(list.getOrder())
                        .member(byMemberId)
                        .document(save)
                        .build();
                if (byMemberId.getMemberCode() == memberCode) {
                  approval.setApprovalStatus(ApprovalStatus.APPROVAL);
                  approval.setApprovalDate(LocalDateTime.now());
                }
                return approval;
              })
              .collect(Collectors.toList());
        approvalRepository.saveAll(approvals);

        //기안리스트가 있지만 1개만 있는데 자기자신일때
      if (approvals.size() == 1 && approvals.get(0).getMember().getMemberCode() == memberCode) {
        save.setDocumentStatus(DocumentStatus.APPROVAL);
      }
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
  public DraftResponse draftSave(int memberCode, DraftRequest draftRequest, List<MultipartFile> multipartFiles){

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Draft draft = modelMapper.map(draftRequest, Draft.class);
    draft.addMember(member);
    draft.setDocumentStatus(DocumentStatus.WAITING);

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
      approval.setApprovalStatus(ApprovalStatus.APPROVAL);
      Approval saveapproval = approvalRepository.save(approval);
      saveapproval.setApprovalDate(save.getCreatedDate());
      save.setDocumentStatus(DocumentStatus.APPROVAL);

    }else{
      List<Approval> approvals = draftRequest.getApprovalList()
              .stream()
              .map(list -> {
                Member byMemberId = memberRepository.findByMemberCode(list.getId());
                Approval approval = Approval.builder()
                        .order(list.getOrder())
                        .member(byMemberId)
                        .document(save)
                        .build();
                if (byMemberId.getMemberCode() == memberCode) {
                  approval.setApprovalStatus(ApprovalStatus.APPROVAL);
                  approval.setApprovalDate(LocalDateTime.now());
                }
                return approval;
              })
              .collect(Collectors.toList());
          approvalRepository.saveAll(approvals);

      if (approvals.size() == 1 && approvals.get(0).getMember().getMemberCode() == memberCode) {
        save.setDocumentStatus(DocumentStatus.APPROVAL);
      }
    }


    if (draftRequest.getExistList() != null && !draftRequest.getExistList().isEmpty()) {
      List<Long> existList = draftRequest.getExistList().stream()
              .map(Integer::longValue)
              .collect(Collectors.toList());
      System.out.println("existList = " + existList);

      List<DocumentFile> existingFiles = documentFileRepository.findAllById(existList);
      existingFiles.forEach(f -> {
        System.out.println("f. = " + f.getFileName());
        System.out.println("f. = " + f.getOriginalName());

      });

      List<DocumentFile> newFiles = existingFiles.stream().map(ex->{
                DocumentFile file = DocumentFile.builder()
                        .fileName(ex.getFileName())
                        .fileType(ex.getFileType())
                        .fileSize(ex.getFileSize())
                        .originalName(ex.getOriginalName())
                        .document(save).build();
                return file;
      }).collect(Collectors.toList());

      documentFileRepository.saveAll(newFiles);
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
          PaymentRequest paymentRequest, List<MultipartFile> multipartFiles){
    System.out.println("paymentRequest = " + paymentRequest);

    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Payment payment = modelMapper.map(paymentRequest, Payment.class);
    payment.addMember(member);

    payment.setCreatedDate(LocalDateTime.now());


    payment.setDocumentStatus(DocumentStatus.WAITING);
    Payment save = paymentDocumentRepository.save(payment);
    save.setCreatedDate(LocalDateTime.now());

    // 참조자
    if(paymentRequest.getRefList() != null){
      List<Integer> result = paymentRequest.getRefList().stream().map(RefRequest::getId).collect(Collectors.toList());

      List<Member> memberList = memberRepository.findByMemberCodeIn(result);

      memberList.forEach(m->{
        DocRef ref = DocRef.builder().document(save).member(m).build();
        docRefRepository.save(ref);
      });
    }
    // 기안리스트가 없을 때 -> 현재 로그인사용자가 결재승인 및 문서 종로
    if(paymentRequest.getApprovalList() == null){
      Approval approval = Approval.builder().order(1).member(member).document(save).build();
      approval.setApprovalStatus(ApprovalStatus.APPROVAL);
      Approval saveapproval = approvalRepository.save(approval);
      saveapproval.setApprovalDate(save.getCreatedDate());
      save.setDocumentStatus(DocumentStatus.APPROVAL);

    }else{
      List<Approval> approvals = paymentRequest.getApprovalList()
              .stream()
              .map(list -> {
                Member byMemberId = memberRepository.findByMemberCode(list.getId());
                Approval approval = Approval.builder()
                        .order(list.getOrder())
                        .member(byMemberId)
                        .document(save)
                        .build();
                if (byMemberId.getMemberCode() == memberCode) {
                  approval.setApprovalStatus(ApprovalStatus.APPROVAL);
                  approval.setApprovalDate(LocalDateTime.now());
                }
                return approval;
              })
              .collect(Collectors.toList());
      approvalRepository.saveAll(approvals);

      if (approvals.size() == 1 && approvals.get(0).getMember().getMemberCode() == memberCode) {
        save.setDocumentStatus(DocumentStatus.APPROVAL);
      }

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


  //문서 임시저장
  public void tempSave(Long documentCode){

    //1. 임시저장 시 documentCode가 없으면 저장되고
    //값들을 받아서 저장하는데 결재리스트는 waiting인 상태로
    // 만약 결재리스트가 있는데

    // 결재리스트가 없다면 본인만 저장


    //2. 임시저장시 documentCode가 있으면 해당내용을 업데이트 시킨다.
    Document document = documentRepository.findById(documentCode).orElseThrow();

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




