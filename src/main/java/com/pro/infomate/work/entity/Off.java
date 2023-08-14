package com.pro.infomate.work.entity;

import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.sql.Timestamp;
import java.util.List;

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

//    @ManyToOne
//    @JoinColumn(name = "MEMBER_CODE")
//    private Member memberCode;

}
