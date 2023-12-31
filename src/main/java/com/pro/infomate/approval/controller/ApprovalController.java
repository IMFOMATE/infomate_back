package com.pro.infomate.approval.controller;

import com.pro.infomate.approval.dto.request.ConfirmRequest;
import com.pro.infomate.approval.service.ApprovalService;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approval")
@RequiredArgsConstructor
@Slf4j
public class ApprovalController {
  
  private final ApprovalService approvalService;

  
  // 승인
  @PatchMapping("/approve")
  public ResponseEntity<ResponseDTO> approve(
          @RequestBody ConfirmRequest confirmRequest,
          @AuthenticationPrincipal MemberDTO memberDTO
  ){
    int memberCode = memberDTO.getMemberCode();
    confirmRequest.setMemberCode(memberCode);

    System.out.println("confirmRequest = " + confirmRequest);

        approvalService.approve(confirmRequest);
    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .build());
  }

  // 반려
  @PatchMapping("/reject")
  public ResponseEntity<ResponseDTO> reject(
          @RequestBody ConfirmRequest confirmRequest,
          @AuthenticationPrincipal MemberDTO memberDTO
          ){
    log.info("[ApprovalController] ConfirmRequest={}", confirmRequest);

    int memberCode = memberDTO.getMemberCode();
    confirmRequest.setMemberCode(memberCode);
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
