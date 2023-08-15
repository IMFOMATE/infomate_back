package com.pro.infomate.calendar.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class ParticipantDTO {

    private int id;
//    private User user;
    private ScheduleDTO schedule;
}
