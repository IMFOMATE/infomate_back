package com.pro.infomate.approval.controller;

import com.pro.infomate.approval.dto.request.*;
import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.dto.response.DraftResponse;
import com.pro.infomate.approval.dto.response.PaymentResponse;
import com.pro.infomate.approval.dto.response.VacationResponse;
import com.pro.infomate.approval.entity.Draft;
import com.pro.infomate.approval.entity.Payment;
import com.pro.infomate.approval.entity.Vacation;
import com.pro.infomate.approval.service.DocRefService;
import com.pro.infomate.approval.service.DocumentService;
import com.pro.infomate.common.*;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
  private final DocumentService documentService;
  private final DocRefService docRefService;


  // 휴가 문서 등록
  @PostMapping("/regist/vacation")
  public ResponseEntity<ResponseDTO> vacationRegist(
          @ModelAttribute VacationRequest vacationRequest,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList,
          @AuthenticationPrincipal MemberDTO memberDTO
  ){

    int memberCode = memberDTO.getMemberCode();

    documentService.saveDocument(memberCode, vacationRequest, fileList, Vacation.class, VacationResponse.class);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  // 기안 문서 등록
  @PostMapping("/regist/draft")
  public ResponseEntity<ResponseDTO> draftRegist(
          @ModelAttribute DraftRequest draftRequest,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList,
          @AuthenticationPrincipal MemberDTO memberDTO
  ){

    System.out.println("form = " + draftRequest);

    int memberCode = memberDTO.getMemberCode();

//
    documentService.saveDocument(memberCode, draftRequest, fileList, Draft.class, DraftResponse.class);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  // 지출결의서 등록
  @PostMapping("/regist/payment")
  public ResponseEntity<ResponseDTO> paymentRegist(
          @ModelAttribute PaymentRequest paymentRequest,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList,
          @AuthenticationPrincipal MemberDTO memberDTO
  ){

    int memberCode = memberDTO.getMemberCode();

    System.out.println("controller = " + paymentRequest.getPaymentList());
    documentService.saveDocument(memberCode, paymentRequest, fileList, Payment.class, PaymentResponse.class);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }


  //임시저장
  @PostMapping("/regist/temp/{documentCode}")
  public ResponseEntity<ResponseDTO> tempRegist(
          @PathVariable(required = false) Long documentCode
  ){





    return null;
  }


  // 문서 삭제
  @DeleteMapping("/delete/{documentId}")
  public ResponseEntity<ResponseDTO> deleteDocument(@PathVariable long documentId){

    System.out.println("documentId = " + documentId);

    documentService.deleteDocument(documentId);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  //결재 메인
  @GetMapping("/main")
  public ResponseEntity<ResponseDTO> documentMain(
          @AuthenticationPrincipal MemberDTO memberDTO
          ){

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(documentService.top5List(memberDTO.getMemberCode()))
                    .build());
  }

  // 상태별 리스트
  @GetMapping("/approval/{docStatus}")
  public ResponseEntity<PagingResponseDTO> approvalList(
          @PathVariable String docStatus,
          @AuthenticationPrincipal MemberDTO memberDTO,
          @RequestParam(required = false, name = "status") String status,
          Pageable pageable
          ){

    log.info("[DocumentController] memberDTO={}", memberDTO);
    int memberCode = memberDTO.getMemberCode();
    log.info("[DocumentController] status={}", status);
    log.info("[DocumentController] docStatus={}", docStatus);

    Criteria criteria = new Criteria(pageable.getPageNumber()+1, pageable.getPageSize());

    Page<DocumentListResponse> documents = null;

    switch (docStatus.toUpperCase()){
      case "APPROVAL":
        documents = documentService.approvalList(status, memberCode, pageable);
        break;
      case "REF":
        documents = docRefService.refPagingList(status, memberCode, pageable);
        break;
      case "TEMPORARY":
        documents = documentService.approvalList("TEMPORARY", memberCode, pageable);
        break;
      case "CREDIT":
        documents = documentService.creditList(memberCode, pageable);
        break;
      case "DEPT":
        documents = documentService.deptList(memberCode, pageable);
        System.out.println("documents = " + documents);
        break;
    }

    PageDTO pageDTO = new PageDTO(criteria, documents.getTotalElements());
    PagingResponseDTO result = PagingResponseDTO.builder()
            .pageInfo(pageDTO)
            .data(documents.getContent())
            .message("success")
            .httpStatus(HttpStatus.OK)
            .build();

    return ResponseEntity.ok()
            .body(result);
  }


  // 문서세부내용
  @GetMapping("/{documentId}")
  public ResponseEntity<ResponseDTO> documentDetail(
          @PathVariable long documentId,
          @AuthenticationPrincipal MemberDTO memberDTO
  ){
    log.info("documentId = {}",documentId);
    log.info("memberDTO={}",memberDTO);

    int memberCode = memberDTO.getMemberCode();

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(documentService.findById(documentId,memberCode))
                    .build());
  }


  // 홈 화면용 리스트
  @GetMapping("/credit")
  public ResponseEntity<ResponseDTO> mainCredit(
          @AuthenticationPrincipal MemberDTO memberDTO){

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(documentService.mainCredit(memberDTO.getMemberCode()))
                    .build());

  }

}
