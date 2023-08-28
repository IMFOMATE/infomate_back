package com.pro.infomate.approval.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class PaymentListResponse {

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate; // 지출 날짜

    @NotBlank
    private String paymentSort; // 지출 종류

    @NotBlank
    private int paymentPrice; // 지출 금액

    @NotBlank
    @Max(value = 50)
    private String paymentContent; // 지출 내용

    private String remarks; //비고

    @Builder
    public PaymentListResponse(LocalDateTime paymentDate, String paymentSort, int paymentPrice, String paymentContent, String remarks) {
        this.paymentDate = paymentDate;
        this.paymentSort = paymentSort;
        this.paymentPrice = paymentPrice;
        this.paymentContent = paymentContent;
        this.remarks = remarks;
    }
}
