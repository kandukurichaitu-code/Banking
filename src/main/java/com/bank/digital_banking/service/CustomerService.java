package com.bank.digital_banking.service;

import com.bank.digital_banking.dto.CreateCustomerRequest;
import com.bank.digital_banking.dto.CustomerResponse;
import com.bank.digital_banking.entity.Customer;
import com.bank.digital_banking.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> getCustomers() {
        return repository.findAll();
    }
    public Customer getCustomerByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));
    }

    public CustomerResponse createCustomer(
            CreateCustomerRequest request) {

        Customer customer = new Customer();

        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setMobileNumber(request.mobileNumber());
        customer.setCustomerType(request.customerType());

        Customer saved = repository.save(customer);

        return new CustomerResponse(
                saved.getCustomerId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getMobileNumber(),
                saved.getCustomerType()
        );
    }
}