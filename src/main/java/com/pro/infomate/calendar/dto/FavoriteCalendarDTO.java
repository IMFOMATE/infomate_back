package com.pro.infomate.calendar.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class FavoriteCalendarDTO {

    private int id;
    private CalendarDTO refCalendar;
    private Integer userId; //수정요망
    private LocalDateTime requestDate;
    private ApprovalStatus approvalStatus;
    private String labelColor;

}
