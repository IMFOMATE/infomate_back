package com.pro.infomate.approval.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class DocumentDetailResponse {

  private Long id; // 문서코드

  @NotBlank
  @Size(min = 5, max = 100)
  private String title; // 제목

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdDate; // 생성일자

  private DocumentStatus documentStatus; // 결재 상태

  private MemberResponse member; // 상신자

  private String content; // 내용

  private String documentKind; // 문서종류

  private List<DocFileResponse> fileList; // 첨부파일

  private List<ApprovalResponse> approvalList; //결재 리스트

  private List<RefMemberResponse> refList; // 참조자

}