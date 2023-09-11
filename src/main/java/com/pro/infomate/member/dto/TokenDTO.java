package com.pro.infomate.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TokenDTO {

    private String grantType;
    private String memberName;
    private String deptName;
    private int deptCode;
    private String rank;
    private int memberCode;
    private String memberPic;
    private String memberPicDefault;
    private String accessToken;
    private Long accessTokenExpiresIn;
}
