package com.pro.infomate.calendar.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@ToString
@Table(name = "TBL_SCHDL")
@SequenceGenerator(
        name = "SEQ_TBL_SCHDL_GEN",
        sequenceName = "SEQ_TBL_SCHDL_SCHDL_ID",
        initialValue = 1, allocationSize = 1
)
public class Schedule {

    @Id
    @Column(name = "SCHDL_ID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TBL_SCHDL_GEN"
    )
    private int id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ALL_DAY")
    private Boolean allDay;

    @Column(name = "COPR_SCHDL")
    private Boolean corpSchdl;

    @Column(name = "REPEAT")
    private Boolean repeat;

    @Column(name = "IMPORTANT")
    private Boolean important;

//    @OneToMany
//    private List<Participant> participantList;

    @Column(name = "REF_CLNDR_ID")
    private int refCalendar;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Calendar.class)
    @JoinColumn(name = "REF_CLNDR_ID",updatable = false, insertable = false)
    private Calendar calendar;
}
