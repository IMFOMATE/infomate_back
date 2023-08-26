package com.pro.infomate.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.calendar.entity.Participant;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDateTime;
import java.util.List;

//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleDTO {

    private int id;
    private String title;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
