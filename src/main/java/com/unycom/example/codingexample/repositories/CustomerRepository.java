package com.unycom.example.codingexample.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import com.unycom.example.codingexample.models.Customer;

public interface CustomerRepository extends Repository<Customer, String> {

    List<Customer> findAll();

    Optional<Customer> findByCode(String code);
}
