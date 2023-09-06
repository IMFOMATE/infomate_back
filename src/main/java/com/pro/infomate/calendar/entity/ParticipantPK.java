package com.pro.infomate.calendar.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ParticipantPK implements Serializable {

    private Integer scheduleCode;

    private Integer memberCode;
}
