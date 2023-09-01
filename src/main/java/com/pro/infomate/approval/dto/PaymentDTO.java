package com.pro.infomate.approval.dto;

import com.pro.infomate.approval.dto.response.ApprovalResponse;
import com.pro.infomate.approval.dto.response.DocFileResponse;
import com.pro.infomate.approval.dto.response.PaymentListResponse;
import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class PaymentDTO extends DocumentDTO{

    @NotBlank
    private List<PaymentListResponse> paymentList;

    @Builder

    public PaymentDTO(Long id, String title, LocalDateTime createdDate, DocumentStatus documentStatus, String content, String documentKind, List<DocFileResponse> fileList, List<ApprovalResponse> approvalList, List<PaymentListResponse> paymentList) {
        super(id, title, createdDate, documentStatus, content, documentKind, fileList, approvalList);
        this.paymentList = paymentList;
    }
}