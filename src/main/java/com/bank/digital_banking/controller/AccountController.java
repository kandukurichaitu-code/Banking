package com.bank.digital_banking.controller;

import com.bank.digital_banking.dto.*;
import com.bank.digital_banking.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public AccountResponse createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        return service.createAccount(request);
    }

    @GetMapping("/customer/{customerId}")
    public List<AccountResponse> getCustomerAccounts(
            @PathVariable Long customerId) {

        return service.getCustomerAccounts(customerId);
    }

    @GetMapping("/{accountNumber}")
    public AccountResponse getAccount(
            @PathVariable String accountNumber) {

        return service.getAccount(accountNumber);
    }

    @PostMapping("/transfer")
    public TransferResponse transferMoney(
            @Valid @RequestBody TransferRequest request) {

        return service.transferMoney(request);
    }
}

