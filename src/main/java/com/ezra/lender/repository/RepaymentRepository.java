package com.ezra.lender.repository;

import com.ezra.lender.model.Repayment;
import com.ezra.lender.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepaymentRepository extends JpaRepository<Repayment,Long> {
    @Query("SELECT r FROM Repayment r where r.user = :user")
    List<Repayment> findByUser(User user);
    Optional<Repayment> findByTransactionRef(String transactionRef);
}
