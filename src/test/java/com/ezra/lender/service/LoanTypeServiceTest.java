package com.ezra.lender.service;


import com.ezra.lender.dto.GeneralResponse;
import com.ezra.lender.model.Loan;
import com.ezra.lender.model.LoanType;
import com.ezra.lender.repository.LoanRepository;
import com.ezra.lender.repository.LoanTypeRepository;
import com.ezra.lender.repository.UserRepository;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
public class LoanTypeServiceTest {
    @MockBean
    LoanTypeRepository loanTypeRepository;

    @Test
    public void testCreateLoanType() {
        LoanType loanType = new LoanType();
        when(loanTypeRepository.findById(Mockito.any())).thenReturn(Optional.of(loanType));
                LoanTypeService loanTypeService = new LoanTypeService(loanTypeRepository);
        ResponseEntity<?> actualCreateElevatorResult = loanTypeService.addLoanType(new LoanType(1L,"name",1,1));
        assertTrue(actualCreateElevatorResult.hasBody());
        GeneralResponse generalResponse= (GeneralResponse) actualCreateElevatorResult.getBody();
         Object payloadResult = generalResponse.getPayload();
        assertEquals("Loan Type Created Successfully", generalResponse.getDescription());
        assertEquals(HttpStatus.CREATED, generalResponse.getStatus());
        assertNull(payloadResult);

    }

    @Test
    void testViewAllLoanTypeInfo() {
        ArrayList<LoanType> content = new ArrayList<>();
        content.add(new LoanType());
        PageImpl<LoanType> pageImpl = new PageImpl<>(content);
        LoanTypeRepository repository = mock(LoanTypeRepository.class);
        when(loanTypeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        ResponseEntity<?> actualViewAllCardsResult = (new LoanTypeService(loanTypeRepository)).findAll();
        List<Object> generalResponse= (List<Object>) actualViewAllCardsResult.getBody();


        assertEquals(HttpStatus.FOUND, actualViewAllCardsResult.getStatusCode());



    }

}
