package com.bank.digital_banking.dto;

import java.math.BigDecimal;

public record AccountResponse(
        Long accountId,
        Long customerId,
        String accountNumber,
        String accountType,
        BigDecimal balance,
        String accountStatus
) {}
