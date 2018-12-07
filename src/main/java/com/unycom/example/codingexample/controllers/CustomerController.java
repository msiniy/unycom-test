package com.unycom.example.codingexample.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.unycom.example.codingexample.models.Customer;
import com.unycom.example.codingexample.repositories.CustomerRepository;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("")
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "customer not found")
    static class CustomerNotFound extends RuntimeException {}

    @GetMapping("/{code}")
    public Customer getCustomer(@PathVariable String code) {
        return customerRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
}
