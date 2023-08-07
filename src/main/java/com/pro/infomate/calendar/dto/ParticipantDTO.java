package com.pro.infomate.calendar.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ParticipantDTO {

    private int id;
//    private User user;
    private ScheduleDTO schedule;
}
