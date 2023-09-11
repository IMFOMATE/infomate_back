package com.pro.infomate.department.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class DepartmentDTO {

    private int deptCode;

    private String deptName;

    @JsonIgnore
    private List<MemberDTO> members;

}