package com.pro.infomate.calendar.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
//@Builder
@ToString
public class CalendarDTO {

    private int id;
    private String name;
    private Boolean openStatus;
    private String labelColor;
    private int indexNo;

    private int memberCode;

    private Integer departmentCode;
    private Boolean defaultCalendar;
    private LocalDateTime createDate;
    private List<FavoriteCalendarDTO> favoriteCalendar;
    private List<ScheduleDTO> refScheduleList;
}
