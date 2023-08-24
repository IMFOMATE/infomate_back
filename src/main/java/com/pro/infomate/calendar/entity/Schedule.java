package com.pro.infomate.calendar.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss")
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
