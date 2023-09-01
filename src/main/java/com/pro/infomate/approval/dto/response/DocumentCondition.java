package com.pro.infomate.approval.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DocumentCondition {

  private Boolean isCredit;

  private Boolean isDept;

  @Builder
  public DocumentCondition(Boolean isCredit, Boolean isDept) {
    this.isCredit = isCredit;
    this.isDept = isDept;
  }
}
