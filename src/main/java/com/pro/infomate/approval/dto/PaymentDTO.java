package com.pro.infomate.approval.dto;

import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO extends DocumentDTO{

    @NotBlank
    private List<PaymentListDTO> paymentList;

    @Builder
    public PaymentDTO(Long id, String title, LocalDateTime createdDate, DocumentStatus documentStatus, String content, String documentKind, List<DocFileDTO> fileList, List<ApprovalDTO> approvalList, List<PaymentListDTO> paymentList) {
        super(id, title, createdDate, documentStatus, content, documentKind, fileList, approvalList);
        this.paymentList = paymentList;
    }
}
