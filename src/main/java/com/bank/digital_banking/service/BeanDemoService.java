package com.bank.digital_banking.service;

import org.springframework.stereotype.Service;

@Service
public class BeanDemoService {

    public BeanDemoService() {
        System.out.println("BeanDemoService constructor called");
    }

    public void test() {
        System.out.println("BeanDemoService method executed");
    }
}