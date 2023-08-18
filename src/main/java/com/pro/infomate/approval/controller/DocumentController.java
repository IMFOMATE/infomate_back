package com.pro.infomate.approval.controller;


import com.pro.infomate.approval.dto.request.DraftRequest;
import com.pro.infomate.approval.dto.request.PaymentRequest;
import com.pro.infomate.approval.dto.request.VacationRequest;
import com.pro.infomate.approval.service.DocumentService;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;

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


  // 휴가 문서 등록
  @PostMapping("/regist/vacation")
  public ResponseEntity<ResponseDTO> vacationRegist(@RequestBody VacationRequest vacationRequest){

    //일단은 code로 사용
    int memberCode = 22;

    documentService.vacationSave(memberCode, vacationRequest);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  // 기안 문서 등록
  @PostMapping("/regist/draft")
  public ResponseEntity<ResponseDTO> draftRegist(@RequestBody DraftRequest draftRequest){

    //일단은 code로 사용
    int memberCode = 2;

    documentService.draftSave(memberCode, draftRequest);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  // 지출결의서 등록
  @PostMapping("/regist/payment")
  public ResponseEntity<ResponseDTO> paymentRegist(@RequestBody PaymentRequest paymentRequest){

    //일단은 code로 사용
    int memberCode = 2;

    documentService.paymentSave(memberCode, paymentRequest);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }





}
