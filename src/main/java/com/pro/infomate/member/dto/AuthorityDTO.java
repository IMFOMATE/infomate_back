package com.pro.infomate.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AuthorityDTO {

    private int authorityCode;

    private String authorityName;

    private String authorityDesc;
}
