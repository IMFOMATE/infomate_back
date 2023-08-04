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

    private Integer userId; //수정요망
    private Integer groupCode;

    private Integer departmentCode;
    private Boolean defaultCalendar;
    private List<FavoriteCalendarDTO> refFavoriteCalendarList;
    private List<ScheduleDTO> refScheduleList;
}