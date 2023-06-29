package com.ezra.lender.controller;

import com.ezra.lender.dto.AddLoanRequest;
import com.ezra.lender.model.User;
import com.ezra.lender.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans/")
@Slf4j
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

     //Customer only
     @Operation(summary = "Add new Loan")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "201", description = "Loan Created Successfully",
                     content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
             @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
             @ApiResponse(responseCode = "404",description = "Resource not found",content = @Content),
             @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @PostMapping("new")
    public ResponseEntity<?> addLoan(Authentication authentication, @RequestBody AddLoanRequest loan){
        String name =authentication.getName();
        return loanService.addLoan(name,loan);

    }
    //Customer only
    @Operation(summary = "Find Existing Member Loan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Loan info returned successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "loan not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})

    @GetMapping("get/member/loan")
    public ResponseEntity<?> findCustomerLoan(Authentication authentication){
        return loanService.getExistingMemberLoan(authentication.getName());

    }
    //Admin only
    @Operation(summary = "List All Loans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Retrieved All Loans",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "not loan found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})

    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return loanService.getAll();

    }
    //Admin only
    @Operation(summary = "Find loan by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Loan Info Returned Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "No Loan with Provided Id found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return loanService.getLoanById(id);

    }
    //Admin






}
