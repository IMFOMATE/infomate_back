package com.pro.infomate.work.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OffDTO {   // 연차

    private int offCode;

    private String offType;

    private Timestamp offDate;

    private int offDay;  // 연차 1, 반차 0.5

    private String offReason;

    private int memberCode;
}
