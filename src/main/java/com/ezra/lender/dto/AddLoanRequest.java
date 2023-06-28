package com.ezra.lender.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddLoanRequest {
    private double principalAmount;
    private String description;
     private long loanTypeId;
}
