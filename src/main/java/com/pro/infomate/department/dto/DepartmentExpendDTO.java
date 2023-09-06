package com.pro.infomate.department.dto;

import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.dto.MemberSumamryDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepartmentExpendDTO {

    private int deptCode;

    private String deptName;

    private List<MemberSumamryDTO> members;

}