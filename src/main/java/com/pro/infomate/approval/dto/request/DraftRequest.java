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
public class DraftRequest extends DocumentRequest{

  private String coDept;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startDate;


  @Builder
  public DraftRequest(@NotBlank @Size(min = 5, max = 100) String title, String content, String emergency, List<RefRequest> refList, List<ApprovalRequest> approvalList, List<Integer> existList, String coDept, LocalDateTime startDate) {
    super(title, content, emergency, refList, approvalList, existList);
    this.coDept = coDept;
    this.startDate = startDate;
  }
}
