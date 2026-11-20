package com.bank.digital_banking.controller;
import com.bank.digital_banking.entity.Customer;
import com.bank.digital_banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);

    }
    @GetMapping
    public List<Customer> getAll() {
        return customerService.getCustomers();
    }
    @GetMapping("/email/{email}")
    public Customer getByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

}
