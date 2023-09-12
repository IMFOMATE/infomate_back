package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Document;
import com.pro.infomate.approval.entity.PaymentList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentListRepository extends JpaRepository<PaymentList, Long> {

  void deleteByDocument(Document document);
}
