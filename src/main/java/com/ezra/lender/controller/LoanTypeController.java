package com.ezra.lender.controller;


import com.ezra.lender.model.LoanType;
import com.ezra.lender.service.LoanTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans/types/")
public class LoanTypeController {
@Autowired
    LoanTypeService loanTypeService;

    @PostMapping("new")
    public ResponseEntity<?> AddLoanType(@RequestBody LoanType loanType){
        return loanTypeService.addLoanType(loanType);

    }
    //Admin only
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return loanTypeService.findAll();

    }
    //Admin only
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return loanTypeService.findCardById(id);

    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return loanTypeService.deleteById(id);

    }
}
