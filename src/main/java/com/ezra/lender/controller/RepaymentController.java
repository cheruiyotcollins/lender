package com.ezra.lender.controller;

import com.ezra.lender.dto.MakePaymentRequest;
import com.ezra.lender.service.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repayments/")
public class RepaymentController {
    @Autowired
    RepaymentService repaymentService;

    //member and admin
    @PostMapping("new")
    public ResponseEntity<?> makePayment(@RequestBody MakePaymentRequest makePaymentRequest){
         return repaymentService.makePayment(makePaymentRequest);

    }
    //list repayment per customer
    @GetMapping("per/customer")
    public ResponseEntity<?> findRepaymentPerCustomer(Authentication authentication){
        String email=authentication.getName();
        return repaymentService.findRepaymentPerCustomer(email);

    }

    //List All Payments made
    //Admin only
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return repaymentService.findAll();

    }
}
