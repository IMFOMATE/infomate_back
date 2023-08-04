package com.pro.infomate.board.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_BOARD")
public class Board {

    @Id
    @Column(name = "BOARD_CODE")
    private int boardCode;

    @Column(name = "BOARD_NAME")
    private String boardName;

    @Column(name = "REF_BOARD")
    private int refBoard;

}
