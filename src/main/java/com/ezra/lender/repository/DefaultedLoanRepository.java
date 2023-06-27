package com.ezra.lender.repository;

import com.ezra.lender.model.DefaultedLoan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultedLoanRepository extends JpaRepository<DefaultedLoan,Long> {
}
