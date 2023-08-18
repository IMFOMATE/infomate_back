package com.pro.infomate.calendar.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarSummaryDTO {

    private Long amount;

    private LocalDate day;

}
