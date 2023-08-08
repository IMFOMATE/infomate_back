package com.pro.infomate.calendar.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarSummaryDTO {

    private int amount;

    private LocalDate day;

}