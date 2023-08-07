package com.pro.infomate.email.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailReferenceDTO {

    private int mailCode;

    private int memberCode;

    private int referenceCode;
}
