
package com.bank.digital_banking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @lombok.Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @Column(name = "ACCOUNT_NUMBER",
            nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private String accountType;

    @Column(name = "BALANCE",
            precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(name = "ACCOUNT_STATUS")
    private String accountStatus;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @PrePersist
    public void beforeInsert() {
        createdDate = LocalDateTime.now();
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        if (accountStatus == null) {
            accountStatus = "ACTIVE";
        }
    }

}
