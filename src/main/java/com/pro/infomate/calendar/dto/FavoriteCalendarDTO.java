package com.pro.infomate.calendar.dto;

import com.pro.infomate.calendar.entity.Calendar;
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

    private int refCalendar;

    private int memberCode; //수정요망

    private LocalDateTime requestDate;

    private ApprovalStatus approvalStatus;

    private String labelColor;

    private Calendar calendar;

}
