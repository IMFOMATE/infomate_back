package com.pro.infomate.email.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDTO {

    private String mailCode;

    private String mailContent;

    private String mailDate;

    private int memberCode;

    private char mailStatus;

    private String mailTitle;

    private char mailLike;

    private String receiverMail;
}
