package com.pro.infomate.addressbook.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_EMAIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(
        name = "SEQ_TBL_MAIL_GENERATOR",
        sequenceName = "SEQ_TBL_MAIL_CODE",
        initialValue = 1, allocationSize = 1
)
public class Email {

    @Id
    @Column(name = "MAIL_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TBL_MAIL_GENERATOR"
    )
    private String mailCode;

    @Column(name = "MAIL_CONTENT")
    private String mailContent;

    @Column(name = "MAIL_DATE")
    private String mailDate;

    @ManyToOne
    @JoinColumn(name = "MEMBER_CODE", nullable = false)
    private Member member;

    @Column(name = "MAIL_STATUS")
    private char mailStatus;

    @Column(name = "MAIL_TITLE")
    private String mailTitle;

    @Column(name = "MAIL_LIKE")
    private char mailLike;

}
