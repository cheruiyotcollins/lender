package com.ezra.lender.service;

import com.ezra.lender.model.DefaultedLoan;
import com.ezra.lender.model.Loan;
import com.ezra.lender.repository.DefaultedLoanRepository;
import com.ezra.lender.repository.LoanRepository;
import com.ezra.lender.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CheckDefaultedLoanScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckDefaultedLoanScheduler.class);
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    DefaultedLoanRepository defaultedLoanRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailNotificationService emailSender;


    //Scheduler, checking and setting loans as defaulted if it has not been cleared within set period

    @Scheduled(fixedDelay = 10000)
    public void defaultLoanChecker() {
        LocalDateTime todaysDate = LocalDateTime.now();
        LOGGER.info("***Start Running scheduled task, checking defaulted loans" + todaysDate);
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Loan> loans = loanRepository.findAll();

        loans.stream().forEach(loan->{

            if(LocalDateTime.now().plusDays(1).isAfter(loan.getDueDate())) {

                new Thread(new Runnable() {
                    public void run() {
                        String sendTo = loan.getUser().getEmail();
                        String subject = "Loan Default Notification";
                        String emailBody = "Dear " + loan.getUser().getName() + "," + "\n" + "\n" + "Your outstanding loan amount of KES "+loan.getOutstandingAmount()+ " was due yesterday." +  "\n" +
                                "Please note that your loan has been marked as defaulted. Clear your outstanding balances to continue using our services"
                                + "\n\n" + "Regards" + "\n" + "Ezra Finance Department";

                        emailSender.sendMail(sendTo, subject, emailBody);

                    }
                }).start();
                DefaultedLoan defaultedLoan=new DefaultedLoan();
                defaultedLoan.setDefaultedOn(todaysDate);
                defaultedLoan.setLoanType(loan.getLoanType());
                defaultedLoan.setDefaultedAmount(loan.getOutstandingAmount());
                defaultedLoan.setUser(loan.getUser());

                defaultedLoanRepository.save(defaultedLoan);
                loanRepository.deleteById(loan.getId());

            }
        });

        LOGGER.info("***Completed" + LocalDateTime.now());

    }
}