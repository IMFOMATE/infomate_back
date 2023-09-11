package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.dto.response.DocumentListResponse;
import com.pro.infomate.approval.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentRepositoryCustom {

  Page<DocumentListResponse> findByDeptDoc(int memberCode, Pageable pageable);

  List<Document> findApprovalsDocument(int memberCode);


  public Page<DocumentListResponse> findAllApproval(String status, int memberCode, Pageable pageable);




  }
