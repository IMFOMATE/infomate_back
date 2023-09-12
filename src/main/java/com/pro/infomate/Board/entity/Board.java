package com.pro.infomate.Board.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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


    @OneToMany
    private List<Post> posts;


}