package com.bank.digital_banking.service;

import com.bank.digital_banking.dto.AccountResponse;
import com.bank.digital_banking.dto.CreateAccountRequest;
import com.bank.digital_banking.dto.TransferRequest;
import com.bank.digital_banking.dto.TransferResponse;
import com.bank.digital_banking.entity.Account;
import com.bank.digital_banking.entity.Customer;
import com.bank.digital_banking.repository.AccountRepository;
import com.bank.digital_banking.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;


    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public AccountResponse createAccount(
            CreateAccountRequest request) {
        Customer customer = customerRepository
                .findById(request.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found"));
        Account account = new Account();
        account.setCustomer(customer);
// Demo account number generator
        account.setAccountNumber(
                "AC" + UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 16)
                        .toUpperCase()
        );
        account.setAccountType(request.accountType());
        account.setBalance(request.openingBalance());
        account.setAccountStatus("ACTIVE");

        Account saved = accountRepository.save(account);

        return toResponse(saved);
    }
    public List<AccountResponse> getCustomerAccounts(
            Long customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer not found");
        }

        return accountRepository
                .findByCustomer_CustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }
    public AccountResponse getAccount(String accountNumber) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"));

        return toResponse(account);
    }
    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getAccountId(),
                account.getCustomer().getCustomerId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getAccountStatus()
        );
    }

    @Transactional
    public TransferResponse transferMoney(TransferRequest request) {

        String fromAccountNumber = request.fromAccount();
        String toAccountNumber = request.toAccount();
        BigDecimal amount = request.amount();

        // 1. Validate source and destination
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Source and destination accounts must be different");

        }

        // 2. Lock accounts in deterministic order to reduce deadlocks
        Account firstLocked;
        Account secondLocked;

        // Lock rows in consistent order
        if (fromAccountNumber.compareTo(toAccountNumber) < 0) {

            firstLocked = accountRepository
                    .findByAccountNumberForUpdate(fromAccountNumber)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Source account not found"));

            secondLocked = accountRepository
                    .findByAccountNumberForUpdate(toAccountNumber)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Destination account not found"));

        } else {

            firstLocked = accountRepository
                    .findByAccountNumberForUpdate(toAccountNumber)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Destination account not found"));

            secondLocked = accountRepository
                    .findByAccountNumberForUpdate(fromAccountNumber)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Source account not found"));
        }


        Account from = firstLocked.getAccountNumber()
                .equals(fromAccountNumber) ? firstLocked : secondLocked;

        Account to = firstLocked.getAccountNumber()
                .equals(toAccountNumber) ? firstLocked : secondLocked;

        // 4. Validate account status
        if (!"ACTIVE".equals(from.getAccountStatus())
                || !"ACTIVE".equals(to.getAccountStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Both accounts must be ACTIVE");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Amount must be greater than zero");


        }

// 5. Validate available balance
        if (from.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Insufficient balance");


        }

// 6. Debit source account
        from.setBalance(from.getBalance().subtract(amount));
    //simulated failure
      //  if (amount.intValueExact()>2000)
        //    throw new RuntimeException("Simulated transfer failure");
// 7. Credit destination account
        to.setBalance(to.getBalance().add(amount));

        // 8. Return response

        return new TransferResponse(
                "Transfer successful",
                fromAccountNumber,
                toAccountNumber,
                amount
        );
    }
}