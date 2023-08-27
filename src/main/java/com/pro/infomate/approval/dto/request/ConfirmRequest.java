package com.pro.infomate.approval.dto.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ConfirmRequest {

  private Long documentCode;

  private int memberCode;

  private String comment;

  @Builder
  public ConfirmRequest(Long documentCode, Integer memberCode, String comment) {
    this.documentCode = documentCode;
    this.memberCode = memberCode;
    this.comment = comment;
  }
}
