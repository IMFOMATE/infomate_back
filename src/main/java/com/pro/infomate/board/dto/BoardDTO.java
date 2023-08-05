package com.pro.infomate.board.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {

    private int boardCode;

    private String boardName;

    private int refBoard;   // 참조
}
