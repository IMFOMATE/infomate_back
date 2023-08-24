package com.pro.infomate.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.calendar.entity.Participant;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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



    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
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
