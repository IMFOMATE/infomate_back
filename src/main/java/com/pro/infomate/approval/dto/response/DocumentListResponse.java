package com.pro.infomate.approval.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.entity.DocumentStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class DocumentListResponse {

  private Long id; // 문서코드

  @NotBlank
  @Size(min = 5, max = 100)
  private String title; // 제목

  private DocumentStatus status; // 결재 상태

  private String emergency; // 긴급여부

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdDate; // 기안 시간
  
  private String documentKind; // 문서종류

  private String auth; // 작성자

  @Builder
  @QueryProjection
  public DocumentListResponse(Long id, String title, DocumentStatus documentStatus, String emergency, LocalDateTime createdDate, String documentKind, String auth) {
    this.id = id;
    this.title = title;
    this.status = documentStatus;
    this.emergency = emergency;
    this.createdDate = createdDate;
    this.documentKind = documentKind;
    this.auth = auth;
  }

  public DocumentListResponse(Document document){
    this.id = document.getId();
    this.title = document.getTitle();
    this.status = document.getDocumentStatus();
    this.emergency = document.getEmergency();
    this.createdDate = document.getCreatedDate();
    this.documentKind = document.getDocumentKind();
    this.auth = document.getMember().getMemberName();
  }

}
