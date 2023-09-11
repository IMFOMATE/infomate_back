package com.pro.infomate.email.dto;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDTO {

    private String mailCode;

    private String mailContent;

    private Date mailDate;

    private Integer memberCode;

    private char mailStatus;

    private String mailTitle;

    private String receiverMail;

    private Integer trashCode;
}
