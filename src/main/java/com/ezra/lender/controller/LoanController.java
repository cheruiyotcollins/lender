package com.ezra.lender.controller;

import com.ezra.lender.dto.AddLoanRequest;
import com.ezra.lender.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans/")
public class LoanController {
    @Autowired
    LoanService loanService;

     //Customer only
    @PostMapping("new")
    public ResponseEntity<?> addLoan(Authentication authentication, @RequestBody AddLoanRequest loan){
        String name =authentication.getName();
        return loanService.addLoan(name,loan);

    }
    //Customer only
    @GetMapping("get/member/loan")
    public ResponseEntity<?> findCustomerLoan(Authentication authentication){
        return loanService.getExistingMemberLoan(authentication.getName());

    }
    //Admin only
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return loanService.getAll();

    }
    //Admin only
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return loanService.getLoanById(id);

    }
    //Admin






}
