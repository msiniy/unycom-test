package com.unycom.example.codingexample.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import com.unycom.example.codingexample.models.Order;

public interface OrderRepository  extends Repository<Order, Long> {

    List<Order> findAll();

    List<Order> findByCustomerCode(String customerCode);

    Optional<Order> findById(Long id);
}
