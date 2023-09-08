package com.pro.infomate.approval.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class VacationRequest extends DocumentRequest{

  @NotBlank
  private String sort;

  @NotBlank
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startDate;

  @NotBlank
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endDate;

  @Builder

  public VacationRequest(@NotBlank @Size(min = 5, max = 100) String title, String content, String emergency, List<RefRequest> refList, List<ApprovalRequest> approvalList, List<Integer> existList, String sort, LocalDateTime startDate, LocalDateTime endDate) {
    super(title, content, emergency, refList, approvalList, existList);
    this.sort = sort;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
