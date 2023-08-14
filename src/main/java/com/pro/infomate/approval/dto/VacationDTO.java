package com.pro.infomate.approval.dto;

import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VacationDTO extends DocumentDTO{

    @NotBlank
    private String sort;

    @NotBlank
    private LocalDateTime startDate;

    @NotBlank
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
