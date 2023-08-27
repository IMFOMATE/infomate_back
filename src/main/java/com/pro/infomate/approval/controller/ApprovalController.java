package com.pro.infomate.approval.controller;

import com.pro.infomate.approval.dto.request.ConfirmRequest;
import com.pro.infomate.approval.service.ApprovalService;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approval")
@RequiredArgsConstructor
@Slf4j
public class ApprovalController {
  
  private final ApprovalService approvalService;

  //결재 대기문서 리스트
  @GetMapping
  public ResponseEntity<ResponseDTO> findApprovalList(){

    //멤버코드를 Auth로 받아오기
    int memberCode = 2;

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(approvalService.ApprovalDocumentList(memberCode))
                    .build());
  }
  
  // 승인
  @PatchMapping("/approve")
  public ResponseEntity<ResponseDTO> approve(@RequestBody ConfirmRequest confirmRequest){
        approvalService.approve(confirmRequest);
    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  // 반려
  @PatchMapping("/reject")
  public ResponseEntity<ResponseDTO> reject(@RequestBody ConfirmRequest confirmRequest){
    log.info("[ApprovalController] ConfirmRequest={}", confirmRequest);

    approvalService.reject(confirmRequest);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  // 상신 취소
  @PatchMapping("/cancel")
  public ResponseEntity<ResponseDTO> cancel(@PathVariable long documentId){
    log.info("[ApprovalController] documentId={}", documentId);

    approvalService.cancel(documentId);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }


}
