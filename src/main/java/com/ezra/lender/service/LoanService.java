package com.ezra.lender.service;

import com.ezra.lender.dto.AddLoanRequest;
import com.ezra.lender.dto.GeneralResponse;
import com.ezra.lender.model.Loan;
import com.ezra.lender.model.LoanType;
import com.ezra.lender.repository.LoanRepository;
import com.ezra.lender.repository.LoanTypeRepository;
import com.ezra.lender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanService {

    private final  LoanRepository loanRepository;
    private final  LoanTypeRepository loanTypeRepository;
    private final    UserRepository userRepository;
    GeneralResponse generalResponse=new GeneralResponse();
    @Autowired
    EmailNotificationService emailSender;

    public ResponseEntity<?> addLoan(String email, AddLoanRequest addLoanRequest){
//        try{
            //Todo check if user is blacklisted

            //checking if user and loan type exists
            if(!loanTypeRepository.existsById(addLoanRequest.getLoanTypeId())) {
                generalResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
                generalResponse.setDescription("Loan Type Not Found");
                return new ResponseEntity<>(generalResponse, HttpStatus.NOT_ACCEPTABLE);
            }
            else if(loanRepository.findByUserId(userRepository.findByEmail(email).get().getId()).isPresent()) {
                generalResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
                generalResponse.setDescription("You have an existing loan, Please repay your loan first");
                return new ResponseEntity<>(generalResponse, HttpStatus.NOT_ACCEPTABLE);

            }else if(userRepository.findByEmail(email).get().getCreditStatus().equals("BAD")){
                generalResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
                generalResponse.setDescription("You have an existing loan, Please repay your loan first");
                return new ResponseEntity<>(generalResponse, HttpStatus.NOT_ACCEPTABLE);
            }
            else{
                //creating and setting up loan
                Loan loan = new Loan();
                LoanType loanType = loanTypeRepository.findById(addLoanRequest.getLoanTypeId()).get();
                loan.setLoanType(loanType);
                loan.setUser(userRepository.findByEmail(email).get());
                loan.setDescription(addLoanRequest.getDescription());
                loan.setStatus("ACTIVE");
                loan.setIssuedDate(LocalDateTime.now());
                loan.setPrincipalAmount(addLoanRequest.getPrincipalAmount());
                double interest = loanType.getInterestPercentage() * addLoanRequest.getPrincipalAmount() / 100;
                loan.setInterest(interest);
                double totalAmount = addLoanRequest.getPrincipalAmount() + interest;
                loan.setTotalAmount(totalAmount);
                loan.setOutstandingAmount(totalAmount);
                loan.setDueDate(LocalDateTime.now().plusMonths(loanType.getLoanDuration()) );
           //Customer Notification
//                new Thread(new Runnable() {
//                    public void run() {
//                        String sendTo = loan.getUser().getEmail();
//                        String subject = "Loan Issuance Notification";
//                        String emailBody = "Dear " + loan.getUser().getName() + "," + "\n" + "\n" + "Your  loan amount of KES "+loan.getPrincipalAmount()+ " has been successfully processed." +  "\n" +
//                                "Please check your phone for a confirmation message. Thank you for being our loyal customer"
//                                + "\n\n" + "Regards" + "\n" + "Ezra Finance Department";
//
//                        emailSender.sendMail(sendTo, subject, emailBody);
//
//                    }
//                }).start();

                loanRepository.save(loan);
                generalResponse.setPayload(loan);
                generalResponse.setStatus(HttpStatus.CREATED);
                generalResponse.setDescription("Loan Issued Successfully");
                return new ResponseEntity<>(generalResponse, HttpStatus.CREATED);
            }


//
//        }catch(Exception e){
//            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
//            generalResponse.setDescription("Something went wrong, loan not issued");
//
//            return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
//        }

    }
    public ResponseEntity<?> getExistingMemberLoan(String email) {
        generalResponse=new GeneralResponse();
       try{ Loan loan=loanRepository.findByUserId(userRepository.findByEmail(email).get().getId()).get();
           if(loan!=null){
               return new ResponseEntity<>(loan,HttpStatus.FOUND);

           }else{
               generalResponse=new GeneralResponse();
               generalResponse.setStatus(HttpStatus.NOT_FOUND);
               generalResponse.setDescription("You do not currently have an existing loan");

           return new ResponseEntity<>(generalResponse,HttpStatus.NOT_FOUND) ;}
       }catch(Exception e){
           generalResponse.setStatus(HttpStatus.BAD_REQUEST);
           generalResponse.setDescription("Something went wrong");
           return new ResponseEntity<>(generalResponse,HttpStatus.BAD_REQUEST) ;
       }


    }

    public ResponseEntity<?> getLoanById(long id){

        try{
            if(loanRepository.existsById(id)){
                return new ResponseEntity<>( loanRepository.findById(id).get(),HttpStatus.FOUND);
            }else{
                generalResponse.setStatus(HttpStatus.NOT_FOUND);
                generalResponse.setDescription("No loan with that ID exists");
                return new ResponseEntity<>( generalResponse,HttpStatus.NOT_FOUND);

            }



        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Something went wrong");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> getAll(){

        try{

            return new ResponseEntity<>( loanRepository.findAll(),HttpStatus.FOUND);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("No Loan Found");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }


}
