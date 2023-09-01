package com.pro.infomate.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro.infomate.department.dto.DepartmentDTO;
import com.pro.infomate.department.entity.Department;
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

    private int memberCode;         // 회원코드

    private String memberPassword;

    private String memberName;

    private String memberId;            // 사번

    private String memberEmail;

    private String memberPhone;

    private String memberNo;            // 생년월일

    private String memberStatus;        // 재직여부

    private String extensionNumber;         // 내선번호

    private String memberAddress;

    private Timestamp hireDate;

    private DepartmentDTO department;

    private String memberPic;

    private RankDTO rank;

    private int memberOff;


    @JsonIgnore
    private List<AuthListDTO> authList;

    @JsonIgnore
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
