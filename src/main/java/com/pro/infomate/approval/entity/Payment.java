package com.pro.infomate.approval.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_PAYMENT")
@DiscriminatorValue("payment")
public class Payment extends Document{

  @OneToMany(mappedBy = "paymentCode", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PaymentList> paymentList;

  public void addPaymentList(PaymentList paymentList){
    this.paymentList.add(paymentList);
    paymentList.setDocument(this);
  }

}
