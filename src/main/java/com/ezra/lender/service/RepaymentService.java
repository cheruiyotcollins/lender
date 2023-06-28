package com.ezra.lender.service;

import com.ezra.lender.dto.GeneralResponse;
import com.ezra.lender.dto.MakePaymentRequest;
import com.ezra.lender.model.Loan;
import com.ezra.lender.model.Repayment;
import com.ezra.lender.repository.LoanRepository;
import com.ezra.lender.repository.RepaymentRepository;
import com.ezra.lender.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RepaymentService {
    @Autowired
    RepaymentRepository repaymentRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    UserRepository userRepository;
    GeneralResponse generalResponse=new GeneralResponse();
    public ResponseEntity<?> makePayment(MakePaymentRequest makePaymentRequest){
        try{
            //checking if user and loan type exists
            //Todo
            if(!userRepository.existsById(makePaymentRequest.getUserId())) {
                generalResponse.setStatus(HttpStatus.NOT_FOUND);
                generalResponse.setDescription("A user with provided id does not exist");

                return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);

            }else if(loanRepository.findByUserId(makePaymentRequest.getUserId()).get()==null){
                generalResponse.setStatus(HttpStatus.NOT_FOUND);
                generalResponse.setDescription("Customer with provided phone number doesn't have an outstanding loan");

                return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);
            }else if(loanRepository.findByUserId(makePaymentRequest.getUserId()).get().getOutstandingAmount()<=0){
                generalResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
                generalResponse.setDescription("loan already cleared");

                return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);
            }
            else{
                //setting up and saving repayment to DB
                Repayment repayment = new Repayment();
                repayment.setAmount(makePaymentRequest.getAmount());
                repayment.setUser(userRepository.findById(makePaymentRequest.getUserId()).get());
                repayment.setMsisdn(makePaymentRequest.getMsisdn());
                repayment.setPaymentChannel(repayment.getPaymentChannel());
                repayment.setTransactionRef(makePaymentRequest.getTransactionRef());
                repayment.setPaymentDate(makePaymentRequest.getPaymentDate());
                repaymentRepository.save(repayment);
                  //todo check to avoid duplicate transaction ref
                //Fetching associated loan and updating outstanding amount
                Loan loan =loanRepository.findByUserId(makePaymentRequest.getUserId()).get();
                loan.setOutstandingAmount(loan.getOutstandingAmount()- makePaymentRequest.getAmount());
                if(loan.getOutstandingAmount()<=0){
                    loan.setStatus("CLEARED");
                }
                loanRepository.save(loan);

                generalResponse.setStatus(HttpStatus.CREATED);
                generalResponse.setDescription("your repayment request has been received, your outstanding loan balance is: "+loan.getOutstandingAmount());
                return new ResponseEntity<>(generalResponse, HttpStatus.CREATED);

            }



        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Something went wrong, repayment not made");

            return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> findRepaymentById(String email,long id){

        try{

            if(loanRepository.findById(id).get().getUser().equals(userRepository.findByEmail(email).get())){
                return new ResponseEntity<>( repaymentRepository.findById(id).get(),HttpStatus.FOUND);
            }else{
                generalResponse.setStatus(HttpStatus.NOT_FOUND);
                generalResponse.setDescription("Repayment not found");
                return new ResponseEntity<>( generalResponse,HttpStatus.NOT_FOUND);

            }



        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Something went wrong, please try again later");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> findAll(){

        try{

            return new ResponseEntity<>( repaymentRepository.findAll(),HttpStatus.FOUND);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("No repayment Found");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> findRepaymentPerCustomer(String email) {
         try{
            return new ResponseEntity<>(repaymentRepository.findByUser(userRepository.findByEmail(email).get()),HttpStatus.FOUND);

         }catch (Exception e){
             generalResponse.setStatus(HttpStatus.BAD_REQUEST);
             generalResponse.setDescription("No repayment Found");
             return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
         }
    }
}