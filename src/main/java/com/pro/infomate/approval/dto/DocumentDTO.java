package com.pro.infomate.approval.dto;

import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class DocumentDTO {

    private Long id;

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;

    private LocalDateTime createdDate;

    private DocumentStatus documentStatus;

    private String content;

    private String documentKind;

    List<DocFileDTO> fileList;

    private List<ApprovalDTO> approvalList;

    public DocumentDTO(Long id, String title, LocalDateTime createdDate, DocumentStatus documentStatus, String content, String documentKind, List<DocFileDTO> fileList, List<ApprovalDTO> approvalList) {
        this.id = id;
        this.title = title;
        this.createdDate = createdDate;
        this.documentStatus = documentStatus;
        this.content = content;
        this.documentKind = documentKind;
        this.fileList = fileList;
        this.approvalList = approvalList;
    }
}
