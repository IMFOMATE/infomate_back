package com.pro.infomate.department.dto;

import com.pro.infomate.member.entity.Member;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class DepartmentListResponse {

    private int deptCode;                   // 부서코드
    private String deptName;                // 부서명
    private String memberName;               // 멤버
    private DeptMemberDTO members;           // 멤버


    @Builder
    public DepartmentListResponse(int deptCode, String deptName, DeptMemberDTO members) {
        this.deptCode = deptCode;
        this.deptName = deptName;
        this.members = members;

//        this.memberName = memberName;
//        this.count = count;
    }



    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class DeptMemberDTO{

        private int deptCode;
        private String memberName;


        @Builder
        public DeptMemberDTO(String memberName) {
            this.memberName = memberName;
        }
    }



}
