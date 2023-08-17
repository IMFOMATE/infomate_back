package com.pro.infomate.approval.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
public class ApprovalDTO {

    private Long id;

    private String comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
