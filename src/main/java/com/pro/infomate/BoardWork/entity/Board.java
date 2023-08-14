package com.pro.infomate.BoardWork.entity;

import lombok.*;

import javax.persistence.*;

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
