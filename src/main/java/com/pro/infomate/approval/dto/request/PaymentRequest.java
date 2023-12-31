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

  public PaymentRequest(@NotBlank @Size(min = 5, max = 100) String title, String content, String emergency, List<RefRequest> refList, List<ApprovalRequest> approvalList, List<Integer> existList, List<PaymentListRequest> paymentList) {
    super(title, content, emergency, refList, approvalList, existList);
    this.paymentList = paymentList;
  }
}
