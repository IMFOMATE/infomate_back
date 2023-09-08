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

  private Boolean isRemove;

  private Boolean isCancel;

  @Builder
  public DocumentCondition(Boolean isCredit, Boolean isDept, Boolean isRemove, Boolean isCancel) {
    this.isCredit = isCredit;
    this.isDept = isDept;
    this.isRemove = isRemove;
    this.isCancel = isCancel;
  }
}
