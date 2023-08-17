package com.pro.infomate.approval.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class DraftRequest extends DocumentRequest{

  private String coDept;

  @Builder
  public DraftRequest(@NotBlank @Size(min = 5, max = 100) String title, String content, String emergency, List<Long> refList, List<ApprovalRequest> approvalList, String coDept) {
    super(title, content, emergency, refList, approvalList);
    this.coDept = coDept;
  }


}
