package com.pro.infomate.calendar.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.calendar.entity.Schedule;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CalendarDTO {

    private int id;
    private String name;
    private Boolean openStatus;
    private String labelColor;
    private Integer indexNo;

    private Integer memberCode;
    private MemberDTO member;

    private Integer departmentCode;
    private Boolean defaultCalendar;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    private List<FavoriteCalendarDTO> favoriteCalendar;
    private List<ScheduleDTO> scheduleList;

    @Override
    public String toString() {
        return "CalendarDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", openStatus=" + openStatus +
                ", labelColor='" + labelColor + '\'' +
                ", indexNo=" + indexNo +
                ", memberCode=" + memberCode +
                ", member=" + member +
                ", departmentCode=" + departmentCode +
                ", defaultCalendar=" + defaultCalendar +
                ", createDate=" + createDate +
                '}';
    }
}
