package com.ezra.lender.service;


import com.ezra.lender.dto.AddLoanRequest;
import com.ezra.lender.dto.GeneralResponse;
import com.ezra.lender.model.Loan;
import com.ezra.lender.repository.LoanRepository;
import com.ezra.lender.repository.LoanTypeRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
public class LoanServiceTest {
    @MockBean
    LoanRepository loanRepository;
    @MockBean
    LoanTypeRepository loanTypeRepository;
    @MockBean
    UserRepository userRepository;


    @Test
    public void testCreateLoanReturnsLoanTypeNotFound() {
        Loan loan = new Loan();
        when(loanRepository.findById(Mockito.any())).thenReturn(Optional.of(loan));
        LoanService loanService = new LoanService(loanRepository, mock(LoanTypeRepository.class),mock(UserRepository.class));
        ResponseEntity<?> actualCreateElevatorResult = loanService.addLoan("kelvincollins86@gmail.com",
                new AddLoanRequest(100,"Manufacturer Name",1L));

        assertTrue(actualCreateElevatorResult.hasBody());
        GeneralResponse generalResponse= (GeneralResponse) actualCreateElevatorResult.getBody();
        Object payloadResult = generalResponse.getPayload();
        assertEquals("Loan Type Not Found", generalResponse.getDescription());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, generalResponse.getStatus());
        assertNull(payloadResult);
     }

//
//    @Test
//    public void testViewElevatorInfoFailsToFindAElevatorInfo() throws Exception {
//        ElevatorInfo elevator = new ElevatorInfo();
//        when(elevatorInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(elevator));
//        ElevatorInfoService elevatorInfoService = new ElevatorInfoService(elevatorInfoRepository,elevatorRepository,eventLogRepository);
//        ResponseEntity<?> actualCreateElevatorResult = elevatorInfoService.createOrUpdateElevatorInfo(
//                new UpdateElevatorInfoRequest(1,1,1L));
//        GeneralResponse generalResponse= (GeneralResponse) actualCreateElevatorResult.getBody();
//
//        assertEquals(HttpStatus.NOT_FOUND, generalResponse.getStatus());
//
//        ResponseEntity<?> failResponse=elevatorInfoService.findById(100L);
//        GeneralResponse generalResponse1= (GeneralResponse) failResponse.getBody();
//        assertEquals("elevator info with provided id not found", generalResponse.getDescription());
//        assertEquals(HttpStatus.NOT_FOUND, generalResponse.getStatus());
//    }
//
    @Test
    void testViewAllElevatorInfo() {
        ArrayList<Loan> content = new ArrayList<>();
        content.add(new Loan());
        PageImpl<Loan> pageImpl = new PageImpl<>(content);
        LoanRepository repository = mock(LoanRepository.class);
        when(loanRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        ResponseEntity<?> actualViewAllCardsResult = (new LoanService(loanRepository, mock(LoanTypeRepository.class),mock(UserRepository.class))).getAll();
        List<Object> generalResponse= (List<Object>) actualViewAllCardsResult.getBody();


        assertEquals(HttpStatus.FOUND, actualViewAllCardsResult.getStatusCode());



    }

}
