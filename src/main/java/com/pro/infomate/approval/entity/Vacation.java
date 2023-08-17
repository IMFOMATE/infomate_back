package com.pro.infomate.approval.entity;

import com.pro.infomate.approval.dto.response.DocumentDetailResponse;
import com.pro.infomate.approval.dto.response.MemberResponse;
import com.pro.infomate.approval.dto.response.VacationResponse;
import com.pro.infomate.approval.service.visitor.DocumentVisitor;
import com.pro.infomate.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_VACATION")
@DiscriminatorValue("vacation")
@DynamicInsert
public class Vacation extends Document{

  @Column(name = "VACATION_SORT")
  private String sort;

  @Column(name = "START_DATE")
  private LocalDateTime startDate;

  @Column(name = "END_DATE")
  private LocalDateTime endDate;

  @Column(name = "VACTION_REASON")
  private String reason;

  @Override
  public DocumentDetailResponse accept(DocumentVisitor<DocumentDetailResponse> visitor) {
    return visitor.visit(this);
  }
}


