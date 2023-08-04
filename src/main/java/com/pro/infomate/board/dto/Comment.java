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
}
