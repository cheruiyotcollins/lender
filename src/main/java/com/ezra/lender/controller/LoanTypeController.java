package com.ezra.lender.controller;


import com.ezra.lender.model.LoanType;
import com.ezra.lender.model.User;
import com.ezra.lender.service.LoanTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans/types/")
@Slf4j
@RequiredArgsConstructor
public class LoanTypeController {
    private final  LoanTypeService loanTypeService;

    @Operation(summary = "Add new Loan Type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Loan Type Created Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Resource not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @PostMapping("new")
    public ResponseEntity<?> AddLoanType(@RequestBody LoanType loanType){
        return loanTypeService.addLoanType(loanType);

    }
    //Admin only
    @Operation(summary = "Get All Loan Loan Types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Loan Types returned Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Resource not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return loanTypeService.findAll();

    }
    //Admin only
    @Operation(summary = "Find Loan Type By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Loan Type Retrieved Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Resource not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return loanTypeService.findCardById(id);

    }

    @Operation(summary = "Delete Loan Type By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan Type Deleted Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Resource not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return loanTypeService.deleteById(id);

    }
}
