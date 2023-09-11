package com.pro.infomate.department.dto;

import com.pro.infomate.member.dto.MemberSumamryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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