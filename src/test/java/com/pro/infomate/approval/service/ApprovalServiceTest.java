package com.pro.infomate.approval.service;

import com.pro.infomate.approval.dto.response.DocumentListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class ApprovalServiceTest {

  @Autowired
  ApprovalService approvalService;

  @Test
  void approvalDocument() {

    List<DocumentListResponse> documentListResponses = approvalService.ApprovalDocumentList(2);
    
    documentListResponses.forEach(d -> System.out.println("d = " + d));
  }
}