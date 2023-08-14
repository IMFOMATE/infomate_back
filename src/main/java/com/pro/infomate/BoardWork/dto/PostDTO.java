package com.pro.infomate.BoardWork.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostDTO {

    private int postCode;

    private String postTitle;

    private Timestamp postDate;

    private String postContents;

    private String boardCategory;

    private int boardCode;

    private int memberCode;

    private Timestamp yearMonth;    // 일자(아마도 년월일표기 할 듯)
}
