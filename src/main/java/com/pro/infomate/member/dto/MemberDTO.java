package com.pro.infomate.member.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO implements UserDetails {

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

    private List<AuthListDTO> authList;

    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.memberPassword;
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
