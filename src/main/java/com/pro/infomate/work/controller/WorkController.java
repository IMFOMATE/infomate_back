package com.pro.infomate.work.controller;

import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.work.dto.response.WorkResponse;
import com.pro.infomate.work.service.WorkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkController {

  private final WorkService workService;

  //1. 출근
  @Operation(summary = "출근", description = "출근을 합니다", tags = {"WorkController"})
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
  @Operation(summary = "퇴근", description = "퇴근을 합니다", tags = {"WorkController"})
  @PatchMapping("/finish")
  public ResponseEntity<ResponseDTO> workFinish(
          @AuthenticationPrincipal MemberDTO memberDTO
  ){

    workService.finish(memberDTO.getMemberCode());

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data("퇴근완료했습니다.")
                    .build());
  }

  @Operation(summary = "오늘 출퇴근 기록 조회", description = "오늘 출퇴근 기록을 조회 합니다", tags = {"WorkController"})
  @GetMapping("/today")
  public ResponseEntity<ResponseDTO> getWork(
          @AuthenticationPrincipal MemberDTO memberDTO
  ){
    WorkResponse byDate = workService.findByDate(LocalDate.now(), memberDTO.getMemberCode());

    System.out.println("byDate = " + byDate);
    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(byDate)
                    .build());
  }


}
