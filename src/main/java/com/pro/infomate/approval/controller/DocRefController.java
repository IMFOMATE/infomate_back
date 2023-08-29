package com.pro.infomate.approval.controller;

import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.service.DocRefService;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.PagingResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ref")
@RequiredArgsConstructor
public class DocRefController {

  private final DocRefService docRefService;

  //참조 문서 5개
  @GetMapping
  public ResponseEntity<ResponseDTO> findRefList(){

    //멤버코드를 Auth로 받아오기
    int memberCode = 22;

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(docRefService.refDocTop5List(memberCode))
                    .build());
  }

  //참조문서 페이징
  @GetMapping("/viewer/{memberCode}")
  public ResponseEntity<ResponseDTO> refListPaging(
          @PathVariable int memberCode,
          Pageable pageable,
          @RequestParam(name = "status", required = false) String status
  ){

    log.info("[DocRefController] status={}", status);

    Page<DocumentListResponse> documents = docRefService.refPagingList(status, memberCode, pageable);
    Criteria criteria = new Criteria(pageable.getPageNumber()+1, pageable.getPageSize());

    log.info("[DocRefController] getContent={}", documents.getContent());
    PageDTO pageDTO = new PageDTO(criteria, documents.getTotalElements());

    PagingResponseDTO result = PagingResponseDTO.builder()
            .pageInfo(pageDTO)
            .data(documents.getContent())
            .build();

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(result)
                    .build());
  }

}
