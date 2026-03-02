package com.example.order_management.util;

import com.example.order_management.model.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class OrderFileLogger {

    private static final String FILE_NAME = "orders.log";

    // synchronized ensures thread-safe file writing
    public synchronized void log(Order order) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            writer.write(order.toString());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}