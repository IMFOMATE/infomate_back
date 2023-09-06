package com.pro.infomate.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro.infomate.department.dto.DepartmentDTO;
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
public class MemberSumamryDTO{

    private int memberCode;

    private String memberName;
    private String memberId;

}
