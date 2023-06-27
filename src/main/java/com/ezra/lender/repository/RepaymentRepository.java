package com.ezra.lender.repository;

import com.ezra.lender.model.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepaymentRepository extends JpaRepository<Repayment,Long> {
}
