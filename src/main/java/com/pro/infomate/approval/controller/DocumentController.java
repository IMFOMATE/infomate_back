package com.pro.infomate.approval.controller;


import com.pro.infomate.approval.dto.request.DraftRequest;
import com.pro.infomate.approval.dto.request.PaymentRequest;
import com.pro.infomate.approval.dto.request.VacationRequest;
import com.pro.infomate.approval.service.DocumentService;
import com.pro.infomate.common.PagingResponseDTO;
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
  @GetMapping("/main/")
  public ResponseEntity<ResponseDTO> documentMain(){

    int memberCode = 2;

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(documentService.top5List(memberCode))
                    .build());
  }

  // 기안문서
  @GetMapping("/approval")
  public ResponseEntity<PagingResponseDTO> approvalAllList(){
    int memberCode = 2;

//    documentService.approvalList()

    return null;
  }



}
