package com.ezra.lender.service;

import com.ezra.lender.model.DefaultedLoan;
import com.ezra.lender.model.Loan;
import com.ezra.lender.model.User;
import com.ezra.lender.repository.DefaultedLoanRepository;
import com.ezra.lender.repository.LoanRepository;
import com.ezra.lender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckDefaultedLoanScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckDefaultedLoanScheduler.class);
    private final LoanRepository loanRepository;
    private final DefaultedLoanRepository defaultedLoanRepository;

    private final    UserRepository userRepository;

    private final  EmailNotificationService emailSender;


    //Scheduler, checking and setting loans as defaulted if it has not been cleared within set period


    //Cron Pattern second, minute, hour, day, month, weekday
    @Value("${loan-expiry}")
     private int loanExpiry;
//    @Scheduled(fixedDelay = 10000)
    @Scheduled(cron="0 0 0 * * *")
    public void defaultLoanChecker() {
        LocalDateTime todaysDate = LocalDateTime.now();
        LOGGER.info("***Start Running scheduled task, checking defaulted loans" + todaysDate);
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Loan> loans = loanRepository.findAll();

        loans.stream().forEach(loan->{

//            if(LocalDateTime.now().minusMonths(loanExpiry).isAfter(loan.getDueDate())) {
                if(LocalDateTime.now().isAfter(loan.getDueDate().plusMonths(loanExpiry))) {

                new Thread(new Runnable() {
                    //Sending email notification
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
                User user= userRepository.findById(loan.getUser().getId()).get();
                user.setCreditStatus("BAD");
                userRepository.save(user);

            }
        });

        LOGGER.info("***Completed" + LocalDateTime.now());

    }
}
