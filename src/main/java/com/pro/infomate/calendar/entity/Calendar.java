package com.pro.infomate.calendar.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "")
public class Calendar {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "OPEN_STATUS")
    private Boolean openStatus;

    @Column(name = "LABEL_COLOR")
    private String labelColor;

    @Column(name = "INDEX")
    private int index;

//   private User refUser; 사용자

    @Column(name = "DEFAULT")
    private Boolean defaultCalendar;

    @Column(name = "DPRMT_CODE")
    private Integer departmentCode;

    @OneToMany
    @JoinColumn(name = "REF_FAVORITE_CLNDR")
    private List<FavoriteCalendar> refFavoriteCalendarList;

    @OneToMany
    @JoinColumn(name = "REF_SCHDL")
    private List<Schedule> refScheduleList;



}
