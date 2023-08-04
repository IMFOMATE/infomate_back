package com.pro.infomate.board.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_POST")
public class Post {

    @Id
    @Column(name = "POST_CODE")
    private int postCode;

    @Column(name = "POST_TITLE")
    private String postTitle;

    @Column(name = "POST_DATE")
    private Timestamp postDate;

    @Column(name = "POST_CONTENTS")
    private String postContents;

    @Column(name = "BOARD_CATEGORY")
    private String boardCategory;

    @Column(name = "BOARD_CODE")
    private int boardCode;

    @Column(name = "MEMBER_CODE")
    private int memberCode;

    @Column(name = "YEAR_MONTH")
    private Timestamp yearMonth;
}
