package com.example.order_management.repository;

import com.example.order_management.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class OrderRepository {

    // Thread-safe in-memory storage
    private final List<Order> orders = new CopyOnWriteArrayList<>();

    // Save order
    public void save(Order order) {
        orders.add(order);
    }

    // Fetch all orders
    public List<Order> findAll() {
        return orders;
    }
}