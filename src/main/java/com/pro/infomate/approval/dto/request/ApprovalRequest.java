package com.pro.infomate.approval.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ApprovalRequest {
  
  private int id; // 멤버 코드
  private int order; // 결재 순서
}
