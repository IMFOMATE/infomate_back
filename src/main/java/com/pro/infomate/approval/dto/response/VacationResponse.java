package com.pro.infomate.approval.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.approval.dto.DocumentDTO;
import com.pro.infomate.approval.entity.*;
import com.pro.infomate.member.entity.Department;
import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @NotBlank
    @Size(min = 10, max = 320)
    private String reason;

    @Builder
    public VacationResponse(Long id, @NotBlank @Size(min = 5, max = 100) String title, LocalDateTime createdDate, DocumentStatus documentStatus, MemberResponse member, String content, String documentKind, List<DocFileResponse> fileList, List<ApprovalResponse> approvalList, List<RefMemberResponse> refList, String sort, LocalDateTime startDate, LocalDateTime endDate, String reason) {
        super(id, title, createdDate, documentStatus, member, content, documentKind, fileList, approvalList, refList);
        this.sort = sort;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

}
