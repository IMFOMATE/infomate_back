package com.pro.infomate.approval.dto.response;

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

    @Builder
    public DraftResponse(Long id, @NotBlank @Size(min = 5, max = 100) String title, LocalDateTime createdDate, DocumentStatus documentStatus, MemberResponse member, String content, String documentKind, List<DocFileResponse> fileList, List<ApprovalResponse> approvalList, List<RefMemberResponse> refList, String coDept) {
        super(id, title, createdDate, documentStatus, member, content, documentKind, fileList, approvalList, refList);
        this.coDept = coDept;
    }
}
