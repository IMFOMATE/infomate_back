package com.pro.infomate.approval.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.entity.DocumentStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class DraftResponse extends DocumentDetailResponse {

    private String coDept;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Builder
    public DraftResponse(Long id, @NotBlank @Size(min = 5, max = 100) String title, LocalDateTime createdDate, DocumentStatus documentStatus, MemberResponse member, String content, String documentKind, String emergency, List<DocFileResponse> fileList, List<ApprovalResponse> approvalList, List<RefMemberResponse> refList, String coDept, LocalDateTime startDate) {
        super(id, title, createdDate, documentStatus, member, content, documentKind, emergency, fileList, approvalList, refList);
        this.coDept = coDept;
        this.startDate = startDate;
    }
}
