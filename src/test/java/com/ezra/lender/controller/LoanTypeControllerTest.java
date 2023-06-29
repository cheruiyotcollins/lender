package com.ezra.lender.controller;


import com.ezra.lender.model.Loan;
import com.ezra.lender.model.LoanType;
import com.ezra.lender.repository.LoanTypeRepository;
import com.ezra.lender.service.LoanService;
import com.ezra.lender.service.LoanTypeService;
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

public class LoanTypeControllerTest {
    @MockBean
    LoanTypeRepository loanTypeRepository;

    @Test
    void testViewAllLoanTypes() {
        ArrayList<LoanType> loanTypes = new ArrayList<>();
        LoanType loanType = new LoanType();
        loanTypes.add(loanType);
        when(loanTypeRepository.findAll()).thenReturn(loanTypes);
        ResponseEntity<?> actualViewAllResult = (new LoanTypeController(new LoanTypeService(loanTypeRepository))).findAll();
        assertTrue(actualViewAllResult.hasBody());
        assertTrue(actualViewAllResult.getHeaders().isEmpty());
        assertEquals(302, actualViewAllResult.getStatusCode().value());
        verify(loanTypeRepository).findAll();
    }
}
