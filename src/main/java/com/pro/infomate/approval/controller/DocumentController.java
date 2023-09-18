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
import io.swagger.v3.oas.annotations.Operation;
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
  @Operation(summary = "휴가문서등록", description = "휴가문서를 등록합니다", tags = {"DocumentController"})
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
  @Operation(summary = "기안문서 등록", description = "기안문서 등록합니다", tags = {"DocumentController"})
  @PostMapping("/regist/draft")
  public ResponseEntity<ResponseDTO> draftRegist(
          @ModelAttribute DraftRequest draftRequest,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList,
          @AuthenticationPrincipal MemberDTO memberDTO
  ){

    System.out.println("form = " + draftRequest);

    int memberCode = memberDTO.getMemberCode();

//
    DraftResponse draftResponse = documentService.saveDocument(memberCode, draftRequest, fileList, Draft.class, DraftResponse.class);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(draftResponse.getTitle() + "등록완료")
                    .build());
  }

  // 지출결의서 등록
  @Operation(summary = "지출결의서 등록", description = "지출결의서 등록합니다", tags = {"DocumentController"})
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
  @Operation(summary = "문서 임시저장", description = "문서입력 후 임시저장을 합니다", tags = {"DocumentController"})
  @PostMapping("/regist/temp/{type}/{documentCode}")
  public ResponseEntity<ResponseDTO> tempRegist(
          @PathVariable(required = false) String documentCode,
          @PathVariable(required = true) String type,
          @ModelAttribute DraftRequest documentRequest,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList,
          @AuthenticationPrincipal MemberDTO memberDTO
          ){

    Long id = documentCode.equals("null") ? null : Long.valueOf(documentCode);



    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }


  @Operation(summary = "기안문서 임시저장 및 임시저장문서 결재요청", description = "임시저장한 기안문서를 수정할 수 있고 결재상신할 수 있습니다", tags = {"DocumentController"})
  @PatchMapping("/temp/draft/{documentCode}")
  public ResponseEntity<ResponseDTO> tempRegistDraft(
          @PathVariable(required = false) String documentCode,
          @ModelAttribute DraftRequest documentRequest,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList,
          @AuthenticationPrincipal MemberDTO memberDTO,
          @RequestParam(required = false) Boolean isSave
  ) {
    Long id = documentCode.equals("null") ? null : Long.valueOf(documentCode);
    System.out.println("isSave = " + isSave);

    documentService.tempSave(id, memberDTO.getMemberCode(), documentRequest, Draft.class, fileList, isSave);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  @Operation(summary = "휴가문서 임시저장 및 임시저장문서 결재요청", description = "임시저장한 휴가문서를 수정할 수 있고 결재상신할 수 있습니다", tags = {"DocumentController"})
  @PatchMapping("/temp/vacation/{documentCode}")
  public ResponseEntity<ResponseDTO> tempRegistVacation(
          @PathVariable(required = false) String documentCode,
          @ModelAttribute VacationRequest documentRequest,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList,
          @AuthenticationPrincipal MemberDTO memberDTO,
          @RequestParam(required = false) Boolean isSave
  ) {
    Long id = documentCode.equals("null") ? null : Long.valueOf(documentCode);


    documentService.tempSave(id, memberDTO.getMemberCode(), documentRequest, Vacation.class, fileList, isSave);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  @Operation(summary = "지출승인서 임시저장 및 임시저장문서 결재요청", description = "임시저장한 지출승인서를 수정할 수 있고 결재상신할 수 있습니다", tags = {"DocumentController"})
  @PatchMapping("/temp/payment/{documentCode}")
  public ResponseEntity<ResponseDTO> tempRegistPayment(
          @PathVariable(required = false) String documentCode,
          @ModelAttribute PaymentRequest documentRequest,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList,
          @AuthenticationPrincipal MemberDTO memberDTO,
          @RequestParam(required = false) Boolean isSave
  ) {
    Long id = documentCode.equals("null") ? null : Long.valueOf(documentCode);

    documentService.tempSave(id, memberDTO.getMemberCode(), documentRequest, Payment.class, fileList, isSave);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }


  // 문서 삭제
  @Operation(summary = "문서 삭제", description = "결재 승인이 되지 않았거나 반려된 상태의 본인문서를 삭제할 수 있씁니다.", tags = {"DocumentController"})
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

  //취소
  @Operation(summary = "문서 삭제", description = "결재 승인이 되지 않았거나 반려된 상태의 본인문서를 삭제할 수 있씁니다.", tags = {"DocumentController"})
  @PatchMapping("/cancel/{documentId}")
  public ResponseEntity<ResponseDTO> cancelDocument(
          @PathVariable long documentId,
          @AuthenticationPrincipal MemberDTO memberDTO
  ){

    System.out.println("documentId = " + documentId);
    log.info("memberDTO ={}", memberDTO);

    documentService.cancelApproval(documentId, memberDTO.getMemberCode());

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }


  //결재 메인
  @Operation(summary = "결재 메인 화면", description = "결재 메인 화면에 보여지는 문서.", tags = {"DocumentController"})
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
  @Operation(summary = "상태별 리스트 조회", description = "문서상태별 리스트를 조회합니다..", tags = {"DocumentController"})
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
  @Operation(summary = "문서 세부내용 조회", description = "문서 세부내용을 조회합니다.", tags = {"DocumentController"})
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
  @Operation(summary = "홈화면 결재문서조회", description = "홈화면에서 사용되는 결재문서 조회입니다", tags = {"DocumentController"})
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
