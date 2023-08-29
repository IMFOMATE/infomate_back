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
    private String accessToken;
    private Long accessTokenExpiresIn;
}
