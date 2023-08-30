package com.pro.infomate.approval.service.visitor;

import com.pro.infomate.approval.entity.Draft;
import com.pro.infomate.approval.entity.Payment;
import com.pro.infomate.approval.entity.Vacation;

public interface DocumentVisitor<T> {
  T visit(Vacation vacation);
  T visit(Payment payment);
  T visit(Draft draft);
}
