package com.pro.infomate.approval.dto;

import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class DraftDTO extends DocumentDTO{

    private String coDept;

    @Builder
    public DraftDTO(Long id, String title, LocalDateTime createdDate, DocumentStatus documentStatus, String content, String documentKind, List<DocFileDTO> fileList, List<ApprovalDTO> approvalList, String coDept) {
        super(id, title, createdDate, documentStatus, content, documentKind, fileList, approvalList);
        this.coDept = coDept;
    }
}
