package com.pro.infomate.calendar.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class DefaultCalendarDTO {

    private CalendarDTO refCalendar;
    private boolean defaultStatus;
}
