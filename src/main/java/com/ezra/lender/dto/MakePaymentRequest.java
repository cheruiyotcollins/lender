package com.ezra.lender.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentRequest {
    @NotEmpty
    private double amount;
    @NotEmpty
    private String msisdn;
    @NotEmpty
    private String paymentChannel;

    @NotEmpty
    private String paymentDate;
    @NotEmpty
    private String transactionRef;
     private long userId;
}
