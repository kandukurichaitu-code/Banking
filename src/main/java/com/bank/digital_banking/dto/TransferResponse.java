package com.bank.digital_banking.dto;

import java.math.BigDecimal;

public record TransferResponse(
        String message,
        String fromAccount,
        String toAccount,
        BigDecimal amount
) {}
