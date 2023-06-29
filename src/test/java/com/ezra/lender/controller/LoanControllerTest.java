package com.ezra.lender.controller;


import com.ezra.lender.dto.AddLoanRequest;
import com.ezra.lender.dto.GeneralResponse;
import com.ezra.lender.model.Loan;
import com.ezra.lender.model.User;
import com.ezra.lender.repository.LoanRepository;
import com.ezra.lender.repository.LoanTypeRepository;
import com.ezra.lender.repository.UserRepository;
import com.ezra.lender.service.LoanService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
public class LoanControllerTest {


    @MockBean
    LoanRepository loanRepository;
    @MockBean
    LoanTypeRepository loanTypeRepository;
    @MockBean
    UserRepository userRepository;
    @Test
    void testViewAllElevators() {
        ArrayList<Loan> elevators = new ArrayList<>();
        Loan card = new Loan();
        elevators.add(card);
        when(loanRepository.findAll()).thenReturn(elevators);
        ResponseEntity<?> actualViewAllResult = (new LoanController(new LoanService(loanRepository, loanTypeRepository,userRepository))).findAll();
        assertTrue(actualViewAllResult.hasBody());
        assertTrue(actualViewAllResult.getHeaders().isEmpty());
        assertEquals(302, actualViewAllResult.getStatusCode().value());
        verify(loanRepository).findAll();
    }




}
