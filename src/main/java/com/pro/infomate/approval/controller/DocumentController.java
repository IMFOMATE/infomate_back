package com.pro.infomate.approval.controller;


import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.dto.request.DraftRequest;
import com.pro.infomate.approval.dto.request.PaymentRequest;
import com.pro.infomate.approval.dto.request.VacationRequest;
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
          @RequestBody VacationRequest vacationRequest,
          @RequestParam(name = "temp",required = false) String temp
  ){

    //일단은 code로 사용
    int memberCode = 22;

    documentService.vacationSave(memberCode, vacationRequest, temp);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  // 기안 문서 등록
  @PostMapping("/regist/draft")
  public ResponseEntity<ResponseDTO> draftRegist(
          @RequestBody DraftRequest draftRequest,
          @RequestParam(name = "temp",required = false) String temp
  ){
    //일단은 code로 사용
    int memberCode = 43;

    documentService.draftSave(memberCode, draftRequest, temp);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  // 지출결의서 등록
  @PostMapping("/regist/payment")
  public ResponseEntity<ResponseDTO> paymentRegist(
          @RequestBody PaymentRequest paymentRequest,
          @RequestParam(name = "temp",required = false) String temp
  ){

    //일단은 code로 사용
    int memberCode = 2;

    documentService.paymentSave(memberCode, paymentRequest,temp );

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
  public ResponseEntity<ResponseDTO> approvalAllList(@PathVariable int memberCode, Pageable pageable){

    Page<Document> documents = documentService.approvalList(memberCode, pageable);

    Criteria criteria = new Criteria(pageable.getPageNumber()+1, pageable.getPageSize());
    PageDTO pageDTO = new PageDTO(criteria, documents.getTotalElements());

    List<DocumentListResponse> data = documents.getContent()
            .stream()
            .map(DocumentListResponse::new)
            .collect(Collectors.toList());

    PagingResponseDTO result = PagingResponseDTO.builder()
            .pageInfo(pageDTO)
            .data(data)
            .build();

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(result)
                    .build());



  }



}
