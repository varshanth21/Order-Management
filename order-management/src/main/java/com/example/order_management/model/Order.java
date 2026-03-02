package com.example.order_management.model;

public class Order {

    private Long orderId;
    private String customerName;
    private String type;      // BUY or SELL
    private Double amount;
    private OrderStatus status;    // NEW, PROCESSED, FAILED

    public Order() {
    }

    public Order(Long orderId, String customerName, String type, Double amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.type = type;
        this.amount = amount;
        this.status = OrderStatus.NEW;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}