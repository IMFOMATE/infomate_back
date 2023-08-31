package com.pro.infomate.Board.dto;

import lombok.*;

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
