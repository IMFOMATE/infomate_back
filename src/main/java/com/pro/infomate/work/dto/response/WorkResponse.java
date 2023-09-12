package com.pro.infomate.work.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.work.entity.WorkStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class WorkResponse {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime;

  private WorkStatus workStatus;

  @Builder
  public WorkResponse(LocalDateTime startTime, LocalDateTime endTime, WorkStatus workStatus) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.workStatus = workStatus;
  }
}
