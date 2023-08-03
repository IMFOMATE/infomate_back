package com.pro.infomate.calendar.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CalendarDTO {

    private int id;
    private String name;
    private Boolean openStatus;
    private String labelColor;
    private int index;

//    private User refUser; 사용자

    private Integer departmentCode;
    private Boolean defaultCalendar;
    private List<FavoriteCalendarDTO> refFavoriteCalendarList;
    private List<ScheduleDTO> refScheduleList;
}
