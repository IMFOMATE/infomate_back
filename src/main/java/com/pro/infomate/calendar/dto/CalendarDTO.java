package com.pro.infomate.calendar.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CalendarDTO {

    private int id;
    private String name;
    private Boolean openStatus;
    private String labelColor;
    private int indexNo;

    private int memberCode;
    private MemberDTO member;

    private Integer departmentCode;
    private Boolean defaultCalendar;
    private LocalDateTime createDate;
    private List<FavoriteCalendarDTO> favoriteCalendar;
    private List<ScheduleDTO> refScheduleList;
}
