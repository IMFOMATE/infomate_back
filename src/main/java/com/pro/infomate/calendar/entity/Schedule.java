package com.pro.infomate.calendar.entity;


import com.pro.infomate.calendar.dto.ScheduleDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "TBL_SCHDL")
@DynamicUpdate
@DynamicInsert
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

//    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.REMOVE})
    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.REMOVE})
//    @JoinColumn(name = "REF_SCHDL_ID")
    private List<Participant> participantList;

    @Column(name = "REF_CLNDR_ID")
    private Integer refCalendar;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Calendar.class)
    @JoinColumn(name = "REF_CLNDR_ID",updatable = false, insertable = false)
    private Calendar calendar;

    public void update(Schedule schedule){
        if(schedule.getTitle() != null) this.title = schedule.getTitle();
        if(schedule.getStartDate() != null) this.startDate = schedule.getStartDate();
        if(schedule.getEndDate() != null) this.endDate = schedule.getEndDate();
        if(schedule.getContent() != null) this.content = schedule.getContent();
        if(schedule.getAddress() != null) this.address = schedule.getAddress();
        if(schedule.getAllDay() != null) this.allDay = schedule.getAllDay();
        if(schedule.getCorpSchdl() != null) this.corpSchdl = schedule.getCorpSchdl();
        if(schedule.getRepeat() != null) this.repeat = schedule.getRepeat();
        if(schedule.getImportant() != null) this.important = schedule.getImportant();
        if(schedule.getRefCalendar() != null) this.refCalendar = schedule.getRefCalendar();
//        if(schedule.getParticipantList().size() > 0) this.setParticipantList(schedule.getParticipantList());
    }
    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", content='" + content + '\'' +
                ", address='" + address + '\'' +
                ", allDay=" + allDay +
                ", corpSchdl=" + corpSchdl +
                ", repeat=" + repeat +
                ", important=" + important +
                ", refCalendar=" + refCalendar +
                '}';
    }
}
