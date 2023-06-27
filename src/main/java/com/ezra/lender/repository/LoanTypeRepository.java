package com.ezra.lender.repository;

import com.ezra.lender.model.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTypeRepository extends JpaRepository<LoanType,Long> {
}
