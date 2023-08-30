package com.pro.infomate.department.dto;


import com.pro.infomate.member.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepartmentDTO {

    private int deptCode;

    private String deptName;
    private List<MemberDTO> members;

}