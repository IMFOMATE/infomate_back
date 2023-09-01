package com.pro.infomate.approval.controller;


import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.dto.request.*;
import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.service.DocumentService;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.PagingResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
  private final ModelMapper modelMapper;

  private final DocumentService documentService;


  // 휴가 문서 등록
  @PostMapping("/regist/vacation")
  public ResponseEntity<ResponseDTO> vacationRegist(
          @ModelAttribute VacationRequest vacationRequest,
          @RequestParam(name = "temp",required = false) String temp,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList
  ){

    //일단은 code로 사용
    int memberCode = 22;

    documentService.vacationSave(memberCode, vacationRequest, temp, fileList);

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
          @RequestParam(name = "temp",required = false) String temp,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList
  ){

    System.out.println("form = " + draftRequest);

    //일단은 code로 사용
    int memberCode = 43;
//
    documentService.draftSave(memberCode, draftRequest, temp ,fileList);

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
          @RequestParam(name = "temp", required = false) String temp,
          @ModelAttribute(name = "fileList") List<MultipartFile> fileList
  ){

    //일단은 code로 사용
    int memberCode = 43;
    System.out.println("controller = " + paymentRequest.getPaymentList());

    documentService.paymentSave(memberCode, paymentRequest, temp, fileList);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }


  // 문서세부내용
  @GetMapping("/{documentId}")
  public ResponseEntity<ResponseDTO> documentDetail(@PathVariable long documentId){
    log.info("documentId = {}",documentId);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(documentService.findById(documentId))
                    .build());
  }

  // 문서 삭제
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDTO> deleteDocument(long documentId){

    log.info("[DocumentController] documentId={}", documentId);
    documentService.deleteDocument(documentId);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  //결재 메인
  @GetMapping("/main/{memberCode}")
  public ResponseEntity<ResponseDTO> documentMain(@PathVariable int memberCode){

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(documentService.top5List(memberCode))
                    .build());
  }

  // 기안문서
  @GetMapping("/approval/{memberCode}")
  public ResponseEntity<ResponseDTO> approvalAllList(
          @PathVariable int memberCode,
          @RequestParam(required = false, name = "status") String status,
          Pageable pageable){

    log.info("[DocumentController] status={}", status);
    log.info("[DocumentController] memberCode={}", memberCode);

    Page<DocumentListResponse> documents = documentService.approvalList(status, memberCode, pageable);
    Criteria criteria = new Criteria(pageable.getPageNumber()+1, pageable.getPageSize());
    log.info("[DocumentController] page={}", pageable.getPageNumber());

    PageDTO pageDTO = new PageDTO(criteria, documents.getTotalElements());

    log.info("[DocumentController] getContent={}", documents.getContent());

    PagingResponseDTO result = PagingResponseDTO.builder()
            .pageInfo(pageDTO)
            .data(documents.getContent())
            .build();

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(result)
                    .build());



  }

  // 결재 대기 문서
  @GetMapping("/credit/{memberCode}")
  public ResponseEntity<ResponseDTO> creditList(
          @PathVariable int memberCode,
          Pageable pageable){

    Page<DocumentListResponse> documents = documentService.creditList(memberCode, pageable);

    Criteria criteria = new Criteria(pageable.getPageNumber()+1, pageable.getPageSize());

    log.info("[DocumentController] page={}", pageable.getPageNumber());

    PageDTO pageDTO = new PageDTO(criteria, documents.getTotalElements());

    log.info("[DocumentController] getContent={}", documents.getContent());

    PagingResponseDTO result = PagingResponseDTO.builder()
            .pageInfo(pageDTO)
            .data(documents.getContent())
            .build();

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(result)
                    .build());
  }


}
