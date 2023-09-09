package com.pro.infomate.work.controller;

import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.work.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkController {

  private final WorkService workService;

  //1. 출근
  @PostMapping("/attend")
  public ResponseEntity<ResponseDTO> workAttend(
          @AuthenticationPrincipal MemberDTO memberDTO
  ){

    workService.attend(memberDTO.getMemberCode());

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data("출근완료했습니다")
                    .build());
  }

  //2. 퇴근
  @PatchMapping("/finish")
  public ResponseEntity<ResponseDTO> workFinish(
          @AuthenticationPrincipal MemberDTO memberDTO
  ){



    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data("퇴근완료했습니다.")
                    .build());
  }

}
