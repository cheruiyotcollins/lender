package com.ezra.lender.service;

import com.ezra.lender.dto.GeneralResponse;
import com.ezra.lender.model.LoanType;
import com.ezra.lender.repository.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanTypeService {
    @Autowired
    LoanTypeRepository loanTypeRepository;
    GeneralResponse generalResponse=new GeneralResponse();

    public ResponseEntity<?> addLoanType(LoanType loanType){
        try{
            loanTypeRepository.save(loanType);
            generalResponse.setStatus(HttpStatus.CREATED);
            generalResponse.setDescription("Loan Type Created Successfully");

            return new ResponseEntity<>(generalResponse, HttpStatus.CREATED);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Loan Type  not created");

            return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> findCardById(long id){

        if(loanTypeRepository.existsById(id)){

                return new ResponseEntity<>( loanTypeRepository.findById(id).get(),HttpStatus.FOUND);
            }else{
                generalResponse.setStatus(HttpStatus.NOT_FOUND);
                generalResponse.setDescription("A Loan Type with provided Id is not available");
                return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);

            }



      
    }
    public ResponseEntity<?> findAll(){

        try{

            return new ResponseEntity<>( loanTypeRepository.findAll(),HttpStatus.FOUND);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("No Loan Found");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteById(long id){

        try{

            if(loanTypeRepository.existsById(id)){
                loanTypeRepository.deleteById(id);
                generalResponse.setStatus(HttpStatus.ACCEPTED);
                generalResponse.setDescription("Loan Type deleted successfully");
                return new ResponseEntity<>( generalResponse,HttpStatus.ACCEPTED);
            }else{
                generalResponse.setStatus(HttpStatus.NOT_FOUND);
                generalResponse.setDescription("A Loan Type  with provided Id is not available");
                return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
            }



        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Soemthing went wrong please try again later");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }
}
