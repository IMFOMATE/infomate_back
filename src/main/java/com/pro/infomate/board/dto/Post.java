package com.pro.infomate.board.dto;

import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.sql.Timestamp;
import java.util.List;

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

//    @Column(name = "BOARD_CODE")
//    private int boardCode;

//    @Column(name = "MEMBER_CODE")
//    private int memberCode;

    @Column(name = "YEAR_MONTH")
    private Timestamp yearMonth;


    @ManyToOne
    @JoinColumn(name = "BOARD_CODE")
    private Board boardCode;

    // 빨간줄떠서 주석처리
//    @OneToMany
//    @JoinColumn(name = "MEMBER_CODE")
//    private List<Member> memberCode;
}
