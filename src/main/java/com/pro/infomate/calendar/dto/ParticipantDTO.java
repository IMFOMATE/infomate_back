package com.pro.infomate.calendar.dto;

import com.pro.infomate.member.dto.MemberDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class ParticipantDTO {

    private MemberDTO member;

    private ScheduleDTO schedule;
}
