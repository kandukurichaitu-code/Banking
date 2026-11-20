package com.bank.digital_banking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest (
    @NotBlank(message = "First name is required")
    @Size(max = 100)
    String firstName,

    @Size(max = 100)
    String lastName,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    String email,

    @Pattern(regexp = "^[0-9]{10}$",
            message = "Mobile must contain 10 digits")
    String mobileNumber,

    @NotBlank(message = "Customer type is required")
    String customerType)
{}
