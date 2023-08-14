package com.pro.infomate.calendar.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CalendarSummary {

    @Id
    @Column(name = "amount")
    private Long amount;

    @Column(name = "day")
    private LocalDate day;

}
