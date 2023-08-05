package com.pro.infomate.board.dto;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_COMMENT")
public class Comment {

    @Id
    @Column(name = "COMMENT_CODE")
    private int commentCode;

    @Column(name = "COMMENT_CONTENTS")
    private String commentContents;

    @Column(name = "POST_CODE")
    private int postCode;

    @Column(name = "MEMBER_CODE")
    private int memberCode;


    @ManyToOne
    @JoinColumn(name = "BOARD_CODE")
    private Board boardCode;

    // 빨간줄떠서 주석처리
//    @OneToMany
//    @JoinColumn(name = "MEMBER_CODE")
//    private List<Member> memberCode;
}
