package com.pro.infomate.approval.entity;

import com.pro.infomate.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_APPROVAL")
@DynamicInsert
@SequenceGenerator(
        name = "APPROVAL_ID_GENERATOR",
        sequenceName = "SEQ_TBL_APPROVAL_APPROVAL_ID",
        initialValue = 1,
        allocationSize = 1
)
public class Approval {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPROVAL_ID_GENERATOR")
  @Column(name = "APPROVAL_ID")
  private Long id;

  @Column(name = "COMMENTS")
  private String comment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DOCUMENT_ID")
  private Document document;

  @Column(name = "APPROVAL_DATE")
  private LocalDateTime approvalDate;

  @Column(name = "APPROVAL_ORDER")
  private int order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_CODE")
  private Member member;

  @ColumnDefault("'WAITING'")
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR2(80) default 'WAITING'" , name = "APPROVAL_STATUS")
  private ApprovalStatus approvalStatus;


  @Builder
  public Approval(int order, Member member, ApprovalStatus approvalStatus, Document document, LocalDateTime approvalDate){
    this.order = order;
    this.member = member;
    this.document = document;
    this.approvalStatus = approvalStatus;
    this.approvalDate = approvalDate;
  }

}
