package com.pro.infomate.work.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_WORK")
public class Work {

    @Id
    @Column(name = "WORK_CODE")
    private int workCode;

    @Column(name = "TIME_START")
    private Timestamp timeStart;

    @Column(name = "TIME_END")
    private Timestamp timeEnd;

    @Column(name = "YEAR_MONTH")
    private Timestamp yearMonth;

    @Column(name = "WORK_STATUS")
    private String workStatus;

    @Column(name = "MEMBER_CODE")
    private int memberCode;
}
