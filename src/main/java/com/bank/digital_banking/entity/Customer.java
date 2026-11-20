package com.bank.digital_banking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "CUSTOMERS")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CUSTOMER_ID")
    private long customerId;

    @Column(name="FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "CUSTOMER_TYPE")
    private String customerType;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @PrePersist
    public void beforeInsert() {
        createdDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "customer",
            fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();
}


