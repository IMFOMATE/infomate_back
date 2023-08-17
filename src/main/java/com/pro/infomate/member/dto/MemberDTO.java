package com.pro.infomate.member.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {

    private int memberCode;

    private String memberPassword;

    private String memberName;

    private String memberId;

    private String memberEmail;

    private String memberPhone;

    private String memberNo;

    private String memberStatus;

    private String extensionNumber;

    private String memberAddress;

    private Timestamp hireDate;

    private int deptCode;

    private String memberPic;

    private int rankCode;

    private int memberOff;
}
