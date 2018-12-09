package com.unycom.example.codingexample.controllers;

import com.unycom.example.codingexample.models.Order;
import com.unycom.example.codingexample.repositories.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("")
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
}
