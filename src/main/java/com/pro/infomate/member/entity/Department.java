package com.pro.infomate.member.entity;

import lombok.*;

import javax.persistence.*;

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
  private Long deptCode;

  @Column(name = "DEPT_NAME")
  private String deptName;

}
