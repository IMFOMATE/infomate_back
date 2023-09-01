package com.pro.infomate.approval.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@ToString
public class ApprovalResponse {

    private int memberCode;

    private String memberName;

    private String rankName;

    private String comment;

    private String profile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvalDate;

    private int order;

    @Builder
    public ApprovalResponse(int memberCode,String memberName, String rankName, String comment, LocalDateTime approvalDate,String profile, int order) {
        this.memberCode = memberCode;
        this.memberName = memberName;
        this.rankName = rankName;
        this.comment = comment;
        this.approvalDate = approvalDate;
        this.order = order;
        this.profile = profile;
    }
}