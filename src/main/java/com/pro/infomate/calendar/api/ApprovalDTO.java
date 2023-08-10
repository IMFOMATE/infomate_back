package com.pro.infomate.calendar.api;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApprovalDTO {

    private int receiver;

    private int sender;

    private String subject;

    private String url;

    private LocalDateTime approvalDate;
}
