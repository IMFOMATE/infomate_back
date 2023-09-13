package com.pro.infomate.email.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date mailDate;

    private Integer memberCode;

    private char mailStatus;

    private String mailTitle;

    private String receiverMail;

    private Integer trashCode;
}
