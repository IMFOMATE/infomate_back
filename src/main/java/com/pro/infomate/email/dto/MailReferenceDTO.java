package com.pro.infomate.email.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailReferenceDTO {

    private Integer mailCode;

    private Integer memberCode;

    private Integer referenceCode;
}
