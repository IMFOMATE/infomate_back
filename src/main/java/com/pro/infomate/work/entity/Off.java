package com.pro.infomate.work.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_OFF")
public class Off {

    @Id
    @Column(name = "OFF_CODE")
    private int offCode;

    @Column(name = "OFF_TYPE")
    private String offType;

    @Column(name = "OFF_DATE")
    private Timestamp offDate;

    @Column(name = "OFF_DAY")
    private int offDay;

    @Column(name = "OFF_REASON")
    private String offReason;

    @Column(name = "MEMBER_CODE")
    private int memberCode;

}
