package com.pro.infomate.member.dto;

import com.pro.infomate.department.entity.Department;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TokenDTO {

    private String grantType;
    private String memberName;
    private String department;
    private String rank;
    private String accessToken;
    private Long accessTokenExpiresIn;
}
