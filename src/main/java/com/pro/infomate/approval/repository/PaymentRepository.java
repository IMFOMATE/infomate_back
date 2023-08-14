package com.pro.infomate.approval.repository;

import com.pro.infomate.approval.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
