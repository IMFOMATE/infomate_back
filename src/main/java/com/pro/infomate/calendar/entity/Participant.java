package com.pro.infomate.calendar.entity;

import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_PARTICIPANT")
@IdClass(ParticipantPK.class)
public class Participant {

    public Participant(int scheduleCode, int memberCode) {
        this.scheduleCode = scheduleCode;
        this.memberCode = memberCode;
    }

    @Id
    @Column(name = "REF_SCHDL_ID")
    private int scheduleCode;

    @Id
    @Column(name = "REF_MEMBER_CODE")
    private int memberCode;

    @ManyToOne
    @JoinColumn(name = "REF_MEMBER_CODE", insertable = false, updatable = false)
    private Member member;
}
