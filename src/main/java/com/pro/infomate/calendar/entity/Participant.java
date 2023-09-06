package com.pro.infomate.calendar.entity;

import com.pro.infomate.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "TBL_PARTICIPANT")
@IdClass(ParticipantPK.class)
public class Participant {

    public Participant(int scheduleCode, int memberCode) {
        this.scheduleCode = scheduleCode;
        this.memberCode = memberCode;
    }

    @Id
    @Column(name = "REF_SCHDL_ID", updatable = false, insertable = false)
    private Integer scheduleCode;

    @Id
    @Column(name = "REF_MEMBER_CODE", updatable = false, insertable = false)
    private Integer memberCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "REF_SCHDL_ID")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "REF_MEMBER_CODE", insertable = false, updatable = false)
    private Member member;
}
