package com.pro.infomate.approval.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startDate;

  @NotBlank
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endDate;

  @NotBlank
  @Size(min = 10, max = 320)
  private String reason;

  @Builder
  public VacationRequest(@NotBlank @Size(min = 5, max = 100) String title, String content, String emergency, List<RefRequest> refList, List<ApprovalRequest> approvalList, String sort, LocalDateTime startDate, LocalDateTime endDate, String reason) {
    super(title, content, emergency, refList, approvalList);
    this.sort = sort;
    this.startDate = startDate;
    this.endDate = endDate;
    this.reason = reason;
  }
}
