package com.pro.infomate.calendar.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.ParticipantDTO;
import com.pro.infomate.calendar.dto.ScheduleDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany
    @JoinColumn(name = "REF_SCHDL_ID")
    private List<Participant> participantList;

    @Column(name = "REF_CLNDR_ID")
    private int refCalendar;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Calendar.class)
    @JoinColumn(name = "REF_CLNDR_ID",updatable = false, insertable = false)
    private Calendar calendar;

    public void update(ScheduleDTO scheduleDTO){
        if(scheduleDTO.getTitle() != null) this.title = scheduleDTO.getTitle();
        if(scheduleDTO.getStartDate() != null) this.startDate = scheduleDTO.getStartDate();
        if(scheduleDTO.getEndDate() != null) this.endDate = scheduleDTO.getEndDate();
        if(scheduleDTO.getContent() != null) this.content = scheduleDTO.getContent();
        if(scheduleDTO.getAddress() != null) this.address = scheduleDTO.getAddress();
        if(scheduleDTO.getAllDay() != null) this.allDay = scheduleDTO.getAllDay();
        if(scheduleDTO.getCorpSchdl() != null) this.corpSchdl = scheduleDTO.getCorpSchdl();
        if(scheduleDTO.getRepeat() != null) this.repeat = scheduleDTO.getRepeat();
        if(scheduleDTO.getImportant() != null) this.important = scheduleDTO.getImportant();
        if(scheduleDTO.getRefCalendar() != null) this.refCalendar = scheduleDTO.getRefCalendar();
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
