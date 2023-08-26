package com.pro.infomate.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteCalendarDTO {

    private int id;

    private int memberCode;

    private MemberDTO refMember;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime requestDate;

    private ApprovalStatus approvalStatus;

    private String labelColor;

    private int refCalendar;

    private CalendarDTO calendar;

}
