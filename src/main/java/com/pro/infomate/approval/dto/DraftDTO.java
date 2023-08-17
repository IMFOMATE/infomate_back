package com.pro.infomate.approval.dto;

import com.pro.infomate.approval.dto.response.ApprovalResponse;
import com.pro.infomate.approval.dto.response.DocFileResponse;
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
    public DraftDTO(Long id, String title, LocalDateTime createdDate, DocumentStatus documentStatus, String content, String documentKind, List<DocFileResponse> fileList, List<ApprovalResponse> approvalList, String coDept) {
        super(id, title, createdDate, documentStatus, content, documentKind, fileList, approvalList);
        this.coDept = coDept;
    }
}
