package com.pro.infomate.approval.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApprovalHomeResponse {

  private List<DocumentListResponse> creditList;
  private List<DocumentListResponse> approvalList;
  private List<DocumentListResponse> refList;

  @Builder
  public ApprovalHomeResponse(List<DocumentListResponse> creditList, List<DocumentListResponse> approvalList, List<DocumentListResponse> refList) {
    this.creditList = creditList;
    this.approvalList = approvalList;
    this.refList = refList;
  }
}
