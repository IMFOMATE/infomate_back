package com.pro.infomate.calendar.dto;

import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class FavoriteCalendarDTO {

    private int id;

    private int memberCode;

    private MemberDTO refMember;

    private LocalDateTime requestDate;

    private ApprovalStatus approvalStatus;

    private String labelColor;

    private int refCalendar;

    private CalendarDTO calendar;

}
