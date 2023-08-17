package com.pro.infomate.approval.controller;

import com.pro.infomate.approval.dto.DraftDTO;
import com.pro.infomate.approval.dto.PaymentDTO;
import com.pro.infomate.approval.dto.PaymentListDTO;
import com.pro.infomate.approval.dto.VacationDTO;
import com.pro.infomate.approval.service.DocumentService;
import com.pro.infomate.calendar.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;

  // 전체 문서
  @GetMapping("/{documentId}")
  public ResponseEntity<ResponseDTO> findAll(@PathVariable long documentId){
    log.info("documentId = {}",documentId);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK.value())
                    .message("success")
                    .data(documentService.findById(documentId))
                    .build());
  }


  // 휴가 문서 등록
  @PostMapping("/regist/vacation")
  public ResponseEntity<ResponseDTO> vacationRegist(@RequestBody VacationDTO vacationDTO){

    //일단은 code로 사용
    int memberCode = 2;

    documentService.vacationSave(memberCode, vacationDTO);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK.value())
                    .message("success")
                    .build());
  }

  // 기안 문서 등록
  @PostMapping("/regist/draft")
  public ResponseEntity<ResponseDTO> draftRegist(@RequestBody DraftDTO draftDTO){

    //일단은 code로 사용
    int memberCode = 2;

    documentService.draftSave(memberCode, draftDTO);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK.value())
                    .message("success")
                    .build());
  }

  // 지출결의서 등록
  @PostMapping("/regist/payment")
  public ResponseEntity<ResponseDTO> paymentRegist(@RequestBody PaymentDTO paymentDTO){

    //일단은 code로 사용
    int memberCode = 2;

    documentService.paymentSave(memberCode, paymentDTO);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK.value())
                    .message("success")
                    .build());
  }





}
