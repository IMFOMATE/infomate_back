package com.pro.infomate.member.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_RANK")
@SequenceGenerator(
        name = "RANK_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_RANK_RANK_CODE",
        initialValue = 1,
        allocationSize = 1
)
public class Rank {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RANK_CODE_GENERATOR")
  @Column(name = "RANK_CODE")
  private Long rankCode;

  @Column(name = "RANK_NAME")
  private String rankName;

  @Column(name = "RANK_PLACE")
  private int rankPlace;

//  @OneToOne(mappedBy = "rank")
//  private Member member;
}
