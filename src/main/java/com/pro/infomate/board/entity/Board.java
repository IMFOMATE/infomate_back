package com.pro.infomate.board.entity;

import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.List;

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
