package com.bank.digital_banking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TRANSACTION_LEDGER")
public class TransactionLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEDGER_ID")
    private Long ledgerId;

    @Setter
    @Column(name = "TRANSACTION_REFERENCE",
            nullable = false, length = 50)
    private String transactionReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @Column(name = "ENTRY_TYPE",
            nullable = false, length = 10)
    private String entryType;

    @Setter
    @Column(name = "AMOUNT",
            nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "DESCRIPTION", length = 250)
    private String description;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
    }

}