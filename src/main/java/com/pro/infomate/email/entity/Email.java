package com.pro.infomate.email.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "TBL_MAIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(
        name = "SEQ_TBL_MAIL_GENERATOR",
        sequenceName = "SEQ_TBL_MAIL_MAIL_CODE",
        initialValue = 1, allocationSize = 1
)

public class Email {

    @Id
    @Column(name = "MAIL_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TBL_MAIL_GENERATOR"
    )
    private Integer mailCode;

    @Column(name = "MAIL_CONTENT")
    private String mailContent;

    @Column(name = "MAIL_DATE")
    private Date mailDate;

    @ManyToOne
    @JoinColumn(name = "MEMBER_CODE", nullable = false)
    @JsonIgnore
    private Member member;

    @Column(name = "MAIL_STATUS")
    private char mailStatus;

    @Column(name = "MAIL_TITLE")
    private String mailTitle;

    @Column(name = "RECEIVER_MAIL")
    private String receiverMail;


    @OneToOne
    @JoinColumn(name = "TRASH_CODE")
    private Trash trash;

}
