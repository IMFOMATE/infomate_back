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
    private DeptMemberDTO deptMember;           // 멤버


    @Builder
    public DepartmentListResponse(int deptCode, String deptName, int count) {
        this.deptCode = deptCode;
        this.deptName = deptName;
//        this.memberName = memberName;
//        this.count = count;
    }



    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class DeptMemberDTO{

        private String memberName;


        @Builder
        public DeptMemberDTO(String memberName) {
            this.memberName = memberName;
        }
    }



}
