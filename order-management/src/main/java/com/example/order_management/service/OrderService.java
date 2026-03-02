package com.example.order_management.service;

import com.example.order_management.exception.OrderProcessingException;
import com.example.order_management.model.Order;
import com.example.order_management.repository.OrderRepository;
import com.example.order_management.util.OrderFileLogger;
import org.springframework.stereotype.Service;
import com.example.order_management.model.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ExecutorService executorService;
    private final OrderFileLogger fileLogger;

    // Constructor Injection (Best Practice)
    public OrderService(OrderRepository repository,
                        ExecutorService executorService,
                        OrderFileLogger fileLogger) {
        this.repository = repository;
        this.executorService = executorService;
        this.fileLogger = fileLogger;
    }

    // -------------------------
    // PLACE ORDER
    // -------------------------
    public void placeOrder(Order order) {

        // Validation
        if (order.getAmount() == null || order.getAmount() <= 0) {
            throw new OrderProcessingException("Order amount must be greater than 0");
        }

        if (!order.getType().equalsIgnoreCase("BUY") &&
                !order.getType().equalsIgnoreCase("SELL")) {
            throw new OrderProcessingException("Order type must be BUY or SELL");
        }

        // Save in memory
        repository.save(order);

        // Process asynchronously
        executorService.submit(() -> processOrder(order));
    }

    // -------------------------
    // PROCESS ORDER (Multithreading)
    // -------------------------
    private void processOrder(Order order) {

        try {
            // Simulate processing delay
            Thread.sleep(1000);

            order.setStatus(OrderStatus.PROCESSED);

            // Log to file
            fileLogger.log(order);

        } catch (Exception e) {
            order.setStatus(OrderStatus.FAILED);
        }
    }

    // -------------------------
    // FETCH ALL ORDERS
    // -------------------------
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    // -------------------------
    // ANALYTICS USING STREAMS
    // -------------------------
    public Map<String, Object> getAnalytics() {

        List<Order> orders = repository.findAll();

        // Total order amount
        double totalAmount = orders.stream()
                .mapToDouble(Order::getAmount)
                .sum();

        // BUY vs SELL count
        Map<String, Long> buySellCount = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getType,
                        Collectors.counting()
                ));

        // Top customer by total volume
        String topCustomer = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomerName,
                        Collectors.summingDouble(Order::getAmount)
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");

        // Group orders by status
        Map<OrderStatus, List<Order>> ordersByStatus =
                orders.stream()
                        .collect(Collectors.groupingBy(Order::getStatus));

        return Map.of(
                "totalAmount", totalAmount,
                "buySellCount", buySellCount,
                "topCustomer", topCustomer,
                "ordersByStatus", ordersByStatus
        );
    }
}