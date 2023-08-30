package com.pro.infomate.member.dto;

import com.pro.infomate.member.entity.Authority;
import lombok.*;

@Getter
@Setter
@ToString
public class AuthListDTO {

    private int memberCode;

    private int authorityCode;

    private AuthorityDTO authority;

    public AuthListDTO() {
    }

    public AuthListDTO(int memberCode, int authorityCode) {
        this.memberCode = memberCode;
        this.authorityCode = authorityCode;
    }

    public AuthListDTO(int memberCode, int authorityCode, AuthorityDTO authority) {
        this.memberCode = memberCode;
        this.authorityCode = authorityCode;
        this.authority = authority;
    }
}
