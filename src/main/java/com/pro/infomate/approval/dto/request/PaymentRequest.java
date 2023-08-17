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
public class PaymentRequest extends DocumentRequest {

  @NotBlank
  private List<PaymentListRequest> paymentList;

  @Builder
  public PaymentRequest(@NotBlank @Size(min = 5, max = 100) String title, String content, String emergency, List<Long> refList, List<ApprovalRequest> approvalList, List<PaymentListRequest> paymentList) {
    super(title, content, emergency, refList, approvalList);
    this.paymentList = paymentList;
  }
}
