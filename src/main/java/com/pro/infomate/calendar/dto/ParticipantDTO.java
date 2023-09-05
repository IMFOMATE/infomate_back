package com.pro.infomate.calendar.dto;

import com.pro.infomate.member.dto.MemberDTO;
import lombok.*;

@Getter
@Setter
@ToString
public class ParticipantDTO {

    private int scheduleCode;

    private int memberCode;

    private MemberDTO member;

    private ScheduleDTO schedule;
}
