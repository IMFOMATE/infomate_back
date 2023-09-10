package com.pro.infomate.approval.entity;

import com.pro.infomate.approval.dto.response.DocumentDetailResponse;
import com.pro.infomate.approval.service.visitor.DocumentVisitor;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_PAYMENT")
@DynamicInsert
@DiscriminatorValue("payment")
public class Payment extends Document{

  @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<PaymentList> paymentList = new ArrayList<>();

  public void addPaymentList(PaymentList paymentList){
    this.paymentList.add(paymentList);

    if(paymentList.getDocument() != this){
      paymentList.setDocument(this);
    }
  }

  @Override
  public DocumentDetailResponse accept(DocumentVisitor<DocumentDetailResponse> visitor) {
    return visitor.visit(this);
  }
}
