package com.pro.infomate.calendar.entity;

import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_PARTICIPANT")
@IdClass(ParticipantPK.class)
public class Participant {

    @Id
    @ManyToOne
    @JoinColumn(name = "REF_SCHDL_ID")
    private Schedule schedule;

    @Id
    @OneToOne
    @JoinColumn(name = "REF_MEMBER_CODE")
    private Member member;
}
