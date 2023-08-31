package com.pro.infomate.approval.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class RefMemberResponse {

  private String memberName; // 이름

  private String rankName; // 직책
  
  private String profile; // 프로필

  @Builder
  public RefMemberResponse(String memberName, String rankName, String profile) {
    this.memberName = memberName;
    this.rankName = rankName;
    this.profile = profile;
  }
}
