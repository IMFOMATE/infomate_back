package com.pro.infomate.member.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "TBL_MEMBER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "MEMBER_CODE")
    private int memberCode;

    @Column(name = "MEMBER_PWD")
    private String memberPassword;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "MEMBER_EMAIL")
    private String memberEmail;

    @Column(name = "MEMBER_PHONE")
    private String memberPhone;

    @Column(name = "MEMBER_NO")
    private String memberNo;

    @Column(name = "MEMBER_STATUS")
    private String memberStatus;

    @Column(name = "EXTENSION_NUMBER")
    private String extensionNumber;

    @Column(name = "MEMBER_ADDRESS")
    private String memberAddress;

    @Column(name = "HIRE_DATE")
    private Timestamp hireDate;

    @Column(name = "DEPT_CODE")
    private int deptCode;

    @Column(name = "MEMBER_PIC")
    private String memberPic;

    @Column(name = "RANK_CODE")
    private int rankCode;

    @Column(name = "MEMBER_OFF")
    private int memberOff;
}
