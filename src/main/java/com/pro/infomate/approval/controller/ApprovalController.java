package com.pro.infomate.approval.controller;

import com.pro.infomate.approval.service.ApprovalService;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approval")
@RequiredArgsConstructor
public class ApprovalController {
  
  private final ApprovalService approvalService;

  //조직도
  @GetMapping("/dept")
  public ResponseEntity<ResponseDTO> deptList(){

  return null ;

  }

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
  
  
}
