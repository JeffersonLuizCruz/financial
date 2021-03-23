package com.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financial.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
