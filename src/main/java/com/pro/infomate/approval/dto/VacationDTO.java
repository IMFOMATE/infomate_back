package com.pro.infomate.approval.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class VacationDTO extends DocumentDTO{

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
    public VacationDTO(Long id, String title, LocalDateTime createdDate, DocumentStatus documentStatus, String content, String documentKind, List<DocFileDTO> fileList, List<ApprovalDTO> approvalList, String sort, LocalDateTime startDate, LocalDateTime endDate, String reason) {
        super(id, title, createdDate, documentStatus, content, documentKind, fileList, approvalList);
        this.sort = sort;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }
}
