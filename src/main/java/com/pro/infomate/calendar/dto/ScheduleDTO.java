package com.pro.infomate.calendar.dto;

import com.pro.infomate.calendar.entity.Participant;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class ScheduleDTO {

    private int id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String content;
    private String address;

    private Boolean allDay;
    private Boolean corpSchdl;
    private Boolean repeat;
    private Boolean important;

    private List<ParticipantDTO> participantList;
    private int refCalendar;
    private CalendarDTO Calendar;

}
