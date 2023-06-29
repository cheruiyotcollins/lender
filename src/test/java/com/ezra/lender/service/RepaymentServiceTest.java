package com.ezra.lender.service;


import com.ezra.lender.model.Loan;
import com.ezra.lender.model.Repayment;
import com.ezra.lender.repository.LoanRepository;
import com.ezra.lender.repository.LoanTypeRepository;
import com.ezra.lender.repository.RepaymentRepository;
import com.ezra.lender.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
public class RepaymentServiceTest {
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
        ArrayList<Repayment> content = new ArrayList<>();
        content.add(new Repayment());
        PageImpl<Repayment> pageImpl = new PageImpl<>(content);
        RepaymentRepository repository = mock(RepaymentRepository.class);
        when(repaymentRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        ResponseEntity<?> actualViewAllCardsResult = (new RepaymentService(repaymentRepository, loanRepository,userRepository,emailSender)).findAll();
        List<Object> generalResponse= (List<Object>) actualViewAllCardsResult.getBody();


        assertEquals(HttpStatus.FOUND, actualViewAllCardsResult.getStatusCode());



    }

}
