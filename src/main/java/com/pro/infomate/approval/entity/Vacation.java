package com.pro.infomate.approval.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_VACATION")
@DiscriminatorValue("vacation")
public class Vacation extends Document{

  @Column(name = "VACATION_SORT")
  private String sort;

  @Column(name = "START_DATE")
  private LocalDateTime startDate;

  @Column(name = "END_DATE")
  private LocalDateTime endDate;

  @Column(name = "VACTION_REASON")
  private String reason;


}
