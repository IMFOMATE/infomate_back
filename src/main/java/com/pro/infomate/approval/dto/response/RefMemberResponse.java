package com.pro.infomate.approval.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class RefMemberResponse {

  private String memberName; // 이름

  private String rankName; // 직책

  @Builder
  public RefMemberResponse(String memberName, String rankName) {
    this.memberName = memberName;
    this.rankName = rankName;
  }
}
