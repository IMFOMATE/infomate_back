package com.pro.infomate.approval.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {

  @NotBlank
  @Size(min = 5, max = 100)
  private String title; //제목

  private String content; // 문서 내용

  private String emergency; // 긴급여부

  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  private List<RefRequest> refList; // 참조자 멤버목록(코드만)

  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  private List<ApprovalRequest> approvalList;// 결재 목록


}
