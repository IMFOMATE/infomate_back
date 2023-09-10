package com.pro.infomate.department.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro.infomate.department.dto.DepartmentDTO;
import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_DEPT")
@SequenceGenerator(
        name = "DEPT_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_DEPT_DEPT_CODE",
        initialValue = 1,
        allocationSize = 1
)
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPT_CODE_GENERATOR")
  @Column(name = "DEPT_CODE")
  private int deptCode;

  @Column(name = "DEPT_NAME")
  private String deptName;

//  @JsonIgnore
  @OneToMany(mappedBy = "department")
  private List<Member> members;

  public void update(DepartmentDTO departmentDTO){
    if(departmentDTO.getDeptName() != null) this.deptName = departmentDTO.getDeptName();
  }

}
