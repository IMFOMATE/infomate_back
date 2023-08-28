package com.pro.infomate.approval.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TBL_PAYMENTLIST")
@SequenceGenerator(
        name = "PAYMENT_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_PAYMENTLIST_PAYMENT_CODE",
        allocationSize = 1,
        initialValue = 1
)
public class PaymentList {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENT_ID_GENERATOR")
  @Column(name = "PAYMENT_CODE")
  private Long paymentCode;

  @Column(name = "PAYMENT_DATE")
  private LocalDateTime paymentDate;

  @Column(name = "PAYMENT_SORT")
  private String paymentSort;

  @Column(name = "PAYMENT_PRICE")
  private int paymentPrice;

  @Column(name = "PAYMENT_CONTENT")
  private String paymentContent;

  @Column(name = "REMARKS")
  private String remarks;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DOCUMENT_ID")
  private Document document;

  @Builder

  public PaymentList(Long paymentCode, LocalDateTime paymentDate, String paymentSort, int paymentPrice, String paymentContent, String remarks, Document document) {
    this.paymentCode = paymentCode;
    this.paymentDate = paymentDate;
    this.paymentSort = paymentSort;
    this.paymentPrice = paymentPrice;
    this.paymentContent = paymentContent;
    this.remarks = remarks;
    this.document = document;
  }
}
