package com.pro.infomate.work.entity;

import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_OFF")
@SequenceGenerator(
        name = "OFF_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_OFF_OFF_CODE",
        initialValue = 1,
        allocationSize = 1
)
public class Off {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFF_CODE_GENERATOR")
    @Column(name = "OFF_CODE")
    private Integer offCode;

    @Column(name = "OFF_TYPE")
    private String offType;

    @Column(name = "OFF_START")
    private LocalDateTime off_start;

    @Column(name = "OFF_END")
    private LocalDateTime off_end;

    @Column(name = "OFF_DAY")
    private int offDay;

    @Column(name = "OFF_REASON")
    private String offReason;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_CODE")
    private Member member;

    @Builder
    public Off(String offType, LocalDateTime off_start, LocalDateTime off_end, int offDay, String offReason, Member member) {
        this.offType = offType;
        this.off_start = off_start;
        this.off_end = off_end;
        this.offDay = offDay;
        this.offReason = offReason;
        this.member = member;
    }
}
