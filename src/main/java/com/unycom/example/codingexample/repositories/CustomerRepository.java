package com.unycom.example.codingexample.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.Repository;
import com.unycom.example.codingexample.models.Customer;

public interface CustomerRepository extends Repository<Customer, String> {
	
	@EntityGraph(value = "Customer.orders", type = EntityGraphType.LOAD)
    List<Customer> findAll();
	
	@EntityGraph(value = "Customer.orders", type = EntityGraphType.LOAD)
    Optional<Customer> findByCode(String code);
}
