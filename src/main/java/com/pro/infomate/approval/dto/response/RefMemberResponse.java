package com.pro.infomate.approval.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class RefMemberResponse {
  private int memberCode;

  private String memberName; // 이름

  private String rankName; // 직책
  
  private String profile; // 프로필

  @Builder
  public RefMemberResponse(int memberCode, String memberName, String rankName, String profile) {
    this.memberCode = memberCode;
    this.memberName = memberName;
    this.rankName = rankName;
    this.profile = profile;
  }
}
