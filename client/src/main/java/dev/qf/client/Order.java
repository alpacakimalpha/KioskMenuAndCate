package dev.qf.client;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String orderTime;
    private String status;
    private List<OrderItem> items = new ArrayList<>();

    public Order(String orderId, String orderTime, String status) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.status = status;
    }

    public String getOrderId() { return orderId; }
    public String getOrderTime() { return orderTime; }
    public String getStatus() { return status; }
    public List<OrderItem> getItems() { return items; }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }
}
