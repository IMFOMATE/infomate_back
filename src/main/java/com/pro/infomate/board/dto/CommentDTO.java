package com.pro.infomate.board.dto;

import lombok.*;

import javax.persistence.Column;

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
