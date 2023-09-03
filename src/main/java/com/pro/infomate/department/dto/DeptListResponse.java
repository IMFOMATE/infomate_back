package com.pro.infomate.department.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeptListResponse {

    private int id;

    private DepartDTO data;

    @Builder
    public DeptListResponse(int id, DepartDTO data) {
        this.id = id;
        this.data = data;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class DepartDTO {
        private String empName;      // 직원이름

        private String empNum;        // 사번

        private String deptName;        // 부서명

        private String rankName;        // 직위명

        private int empCode;        // 직원코드

        private int rank;

        @Builder
        public DepartDTO(String empName, String empNum, String deptName, String rankName, int empCode, int rank) {
            this.empName = empName;
            this.empNum = empNum;
            this.deptName = deptName;
            this.rankName = rankName;
            this.empCode = empCode;
            this.rank = rank;
        }
    }

}
