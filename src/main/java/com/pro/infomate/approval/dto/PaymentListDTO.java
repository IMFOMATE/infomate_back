package com.pro.infomate.approval.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class PaymentListDTO {

    @NotBlank
    private LocalDateTime paymentDate;

    @NotBlank
    private String paymentSort;

    @NotBlank
    private int paymentPrice;

    @NotBlank
    @Max(value = 50)
    private String paymentContent;

    private String remarks; //비고

    private String paymentReason;


}
