package com.pro.infomate.approval.controller;

import com.pro.infomate.approval.service.DocumentService;
import com.pro.infomate.calendar.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;

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




}
