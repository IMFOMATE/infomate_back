package com.pro.infomate.approval.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.approval.dto.response.*;
import com.pro.infomate.approval.entity.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class VacationResponse extends DocumentDetailResponse {

    @NotBlank
    private String sort;

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Builder
    public VacationResponse(Long id, @NotBlank @Size(min = 5, max = 100) String title, LocalDateTime createdDate, DocumentStatus documentStatus, MemberResponse member, String content, String documentKind, String emergency, List<DocFileResponse> fileList, List<ApprovalResponse> approvalList, List<RefMemberResponse> refList, DocumentCondition condition, String sort, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, createdDate, documentStatus, member, content, documentKind, emergency, fileList, approvalList, refList, condition);
        this.sort = sort;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
