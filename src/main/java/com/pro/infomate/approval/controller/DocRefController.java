package com.pro.infomate.approval.controller;

import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.service.DocRefService;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ref")
@RequiredArgsConstructor
public class DocRefController {

  private final DocRefService docRefService;


  @GetMapping
  public ResponseEntity<ResponseDTO> findAll(){

    //멤버코드를 Auth로 받아오기
    int memberCode = 22;

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(docRefService.refDocList(memberCode))
                    .build());
  }
}
