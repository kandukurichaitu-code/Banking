package com.bank.digital_banking.dto;

public record CustomerResponse(
        Long customerId,
        String firstName,
        String lastName,
        String email,
        String mobileNumber,
        String customerType
) {}
