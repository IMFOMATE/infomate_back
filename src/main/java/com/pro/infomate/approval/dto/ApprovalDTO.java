package com.pro.infomate.approval.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
public class ApprovalDTO {

    private Long id;

    private String comment;

    private LocalDateTime approvalDate;

    private int order;


    @Builder
    public ApprovalDTO(Long id, String comment, LocalDateTime approvalDate, int order) {
        this.id = id;
        this.comment = comment;
        this.approvalDate = approvalDate;
        this.order = order;
    }
}
