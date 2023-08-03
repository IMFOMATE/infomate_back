package com.pro.infomate.calendar.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "")
public class Schedule {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "ADDRRESS")
    private String address;

    @OneToMany
    @JoinColumn(name = "REF_PARTICIPANT")
    private List<Participant> participantList;

    @Column(name = "ALL_DAY")
    private Boolean allDay;

    @Column(name = "CORP_SCHDL")
    private Boolean corpSchdl;

    @Column(name = "REPEAT")
    private Boolean repeat;

    @Column(name = "IMPORTANT")
    private Boolean important;

    @ManyToOne
    @JoinColumn(name = "REF_CLNDR", updatable = false)
    private Calendar refCalendar;

}
