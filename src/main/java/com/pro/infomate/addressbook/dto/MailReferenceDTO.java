package com.pro.infomate.addressbook.dto;

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
