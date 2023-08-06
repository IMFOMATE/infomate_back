package com.pro.infomate.calendar.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ScheduleDTO {

    private int id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String content;
    private String address;
//    private List<ParticipantDTO> participantList;
    private Boolean allDay;
    private Boolean corpSchdl;
    private Boolean repeat;
    private Boolean important;
    private int refCalendar;
    private CalendarDTO Calendar;

}
