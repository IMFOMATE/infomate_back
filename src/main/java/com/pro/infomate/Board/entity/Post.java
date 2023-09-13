package com.pro.infomate.Board.entity;

import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_POST")
@SequenceGenerator(
        name = "POST_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_POST_POST_CODE",
        initialValue = 1,   // 초기번호
        allocationSize = 1  // 1씩 증가
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_CODE_GENERATOR")
    @Column(name = "POST_CODE")
    private int postCode;

    @Column(name = "POST_TITLE")
    private String postTitle;

    @Column(name = "POST_DATE")
    private Date postDate;

    @Column(name = "POST_CONTENTS")
    private String postContents;

    @Column(name = "BOARD_CATEGORY")
    private String boardCategory;

    @Column(name = "BOARD_CODE")
    private int boardCode;

//    @Column(name = "MEMBER_CODE")
//    private int memberCode;

    @ManyToOne
    @JoinColumn(name = "BOARD_CODE" ,insertable = false,updatable = false)
    private Board board;

    @OneToMany
    @JoinColumn(name = "FILE_NAME")
    private List<BoardFile> boardFile;

    @ManyToOne
    @JoinColumn(name = "MEMBER_CODE")
    private Member member;

}
