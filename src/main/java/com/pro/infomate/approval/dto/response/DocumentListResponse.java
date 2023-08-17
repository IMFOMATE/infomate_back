package com.pro.infomate.approval.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class DocumentListResponse {

  private Long id; // 문서코드

  @NotBlank
  @Size(min = 5, max = 100)
  private String title; // 제목

  private DocumentStatus documentStatus; // 결재 상태

  private String emergency; // 긴급여부

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdDate; // 기안 시간
  
  private String documentKind; // 문서종류

  @Builder
  public DocumentListResponse(Long id, String title, DocumentStatus documentStatus, String emergency, LocalDateTime createdDate) {
    this.id = id;
    this.title = title;
    this.documentStatus = documentStatus;
    this.emergency = emergency;
    this.createdDate = createdDate;
  }
}
