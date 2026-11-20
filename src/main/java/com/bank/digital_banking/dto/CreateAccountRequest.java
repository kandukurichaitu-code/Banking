package com.bank.digital_banking.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateAccountRequest(

        @NotNull
        Long customerId,

        @NotBlank
        String accountType,

        @NotNull
        @DecimalMin("0.00")
        @Digits(integer = 17, fraction = 2)
        BigDecimal openingBalance

) {}
