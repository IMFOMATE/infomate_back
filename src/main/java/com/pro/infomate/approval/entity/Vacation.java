package com.pro.infomate.approval.entity;

import com.pro.infomate.approval.dto.response.DocumentDetailResponse;
import com.pro.infomate.approval.service.visitor.DocumentVisitor;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_VACATION")
@DiscriminatorValue("vacation")
@DynamicInsert
@ToString(callSuper = true)
public class Vacation extends Document{

  @Column(name = "VACATION_SORT")
  private String sort;

  @Column(name = "START_DATE")
  private LocalDateTime startDate;

  @Column(name = "END_DATE")
  private LocalDateTime endDate;

  @Override
  public DocumentDetailResponse accept(DocumentVisitor<DocumentDetailResponse> visitor) {
    return visitor.visit(this);
  }
}


