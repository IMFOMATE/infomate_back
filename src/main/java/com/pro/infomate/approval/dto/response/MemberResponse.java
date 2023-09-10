package com.pro.infomate.approval.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class MemberResponse {

  private int memberCode;

  private String memberName;

  private String deptName;
  @Builder
  public MemberResponse(int memberCode, String memberName, String deptName) {
    this.memberCode = memberCode;
    this.memberName = memberName;
    this.deptName = deptName;
  }
}