package com.pro.infomate.calendar.dto;

import com.pro.infomate.calendar.entity.Calendar;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class FavoriteCalendarDTO {

    private int id;

    private int memberCode;

    private LocalDateTime requestDate;

    private ApprovalStatus approvalStatus;

    private String labelColor;

    private int refCalendar;

    private CalendarDTO calendar;

}
