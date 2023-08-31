package com.pro.infomate.Board.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {

    private int commentCode;

    private String commentContents;

    private int postCode;

    private int memberCode;
}
