package com.ezra.lender.controller;

import com.ezra.lender.dto.MakePaymentRequest;
import com.ezra.lender.model.User;
import com.ezra.lender.service.RepaymentService;
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
@RequestMapping("/repayments/")
@Slf4j
@RequiredArgsConstructor
public class RepaymentController {
    private final RepaymentService repaymentService;

    //member and admin
    @Operation(summary = "Make Payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Repayment made Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Resource not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @PostMapping("new")
    public ResponseEntity<?> makePayment(@RequestBody MakePaymentRequest makePaymentRequest){
         return repaymentService.makePayment(makePaymentRequest);

    }
    //list repayment per customer
    @Operation(summary = "Find Repayment Per Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Repayment Per Customer Retrieved Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Resource not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("per/customer")
    public ResponseEntity<?> findRepaymentPerCustomer(Authentication authentication){
        String email=authentication.getName();
        return repaymentService.findRepaymentPerCustomer(email);

    }

    //List All Payments made
    //Admin only
    @Operation(summary = "Get All Repayments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "All Repayments Retrieved Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Resource not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return repaymentService.findAll();

    }
}
