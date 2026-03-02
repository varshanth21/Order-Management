package com.example.order_management.controller;

import com.example.order_management.model.Order;
import com.example.order_management.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    // Constructor Injection
    public OrderController(OrderService service) {
        this.service = service;
    }

    // --------------------------------
    // PLACE ORDER (POST)
    // --------------------------------
    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {

        service.placeOrder(order);

        return ResponseEntity
                .status(201)
                .body("Order received and processing started.");
    }

    // --------------------------------
    // GET ALL ORDERS
    // --------------------------------
    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    // --------------------------------
    // GET ANALYTICS
    // --------------------------------
    @GetMapping("/analytics")
    public Map<String, Object> getAnalytics() {
        return service.getAnalytics();
    }
}