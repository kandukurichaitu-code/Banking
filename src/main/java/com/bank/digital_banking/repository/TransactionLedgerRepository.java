package com.bank.digital_banking.repository;

import com.bank.digital_banking.entity.TransactionLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionLedgerRepository
        extends JpaRepository<TransactionLedger, Long> {

    List<TransactionLedger> findByAccount_AccountIdOrderByCreatedDateDesc(
            Long accountId);

    List<TransactionLedger> findByTransactionReference(
            String transactionReference);
}