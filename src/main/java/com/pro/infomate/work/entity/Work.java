package com.pro.infomate.work.entity;

import com.pro.infomate.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_WORK")
@SequenceGenerator(
        name = "WORK_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_WORK_WORK_CODE",
        initialValue = 1,
        allocationSize = 1
)
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORK_CODE_GENERATOR")
    @Column(name = "WORK_CODE")
    private Integer workCode;

    @Column(name = "TIME_START")
    private LocalDateTime timeStart;

    @Column(name = "TIME_END")
    private LocalDateTime timeEnd;

    @Column(name = "YEAR_MONTH")
    private LocalDate yearMonth;

    @ColumnDefault("'START'")
    @Column(name = "WORK_STATUS")
    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_CODE")
    private Member member;

    @Builder
    public Work(LocalDateTime timeStart, LocalDateTime timeEnd, LocalDate yearMonth, WorkStatus workStatus, Member member) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.yearMonth = yearMonth;
        this.workStatus = workStatus;
        this.member = member;
    }
}
