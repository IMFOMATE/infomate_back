package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.dto.response.DocumentListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocRefRepositoryCustom {

  Page<DocumentListResponse> refPagingList(int memberCode, Pageable pageable);
}
