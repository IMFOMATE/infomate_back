package com.pro.infomate.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ScheduleDTO {

    private int id;
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    private String content;
    private String address;

    private Boolean allDay;
    private Boolean corpSchdl;
    private Boolean repeat;
    private Boolean important;

    private List<ParticipantDTO> participantList;
    private Integer refCalendar;
    private CalendarDTO Calendar;

}
