package com.ezra.lender.controller;


import com.ezra.lender.model.LoanType;
import com.ezra.lender.model.Repayment;
import com.ezra.lender.repository.LoanRepository;
import com.ezra.lender.repository.RepaymentRepository;
import com.ezra.lender.repository.UserRepository;
import com.ezra.lender.service.EmailNotificationService;
import com.ezra.lender.service.LoanTypeService;
import com.ezra.lender.service.RepaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
public class RepaymentControllerTest {

    @MockBean
     RepaymentRepository repaymentRepository;
    @MockBean
    LoanRepository loanRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean

     EmailNotificationService emailSender;

    @Test
    void testViewAllRepayments() {
        ArrayList<Repayment> repayments = new ArrayList<>();
        Repayment repayment = new Repayment();
        repayments.add(repayment);
        when(repaymentRepository.findAll()).thenReturn(repayments);
        ResponseEntity<?> actualViewAllResult = (new RepaymentController(new RepaymentService(repaymentRepository,loanRepository,userRepository,emailSender))).findAll();
        assertTrue(actualViewAllResult.hasBody());
        assertTrue(actualViewAllResult.getHeaders().isEmpty());
        assertEquals(302, actualViewAllResult.getStatusCode().value());
        verify(repaymentRepository).findAll();
    }

}


