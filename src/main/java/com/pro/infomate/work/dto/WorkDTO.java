package com.pro.infomate.work.dto;

import lombok.*;

import javax.persistence.Column;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WorkDTO {  // 근무

    private int workCode;

    private Timestamp timeStart;    // 출근시간

    private Timestamp timeEnd;      // 퇴근시간

    private Timestamp yearMonth;    // 일자인데.. 이름을 걍 이렇게 쓰는걸로

    private String workStatus;

    private int memberCode;
}
