package dev.qf.client;

import java.util.List;

public interface OrderService {
    List<Order> getOrderList();
    void acceptOrder(String orderId);
    void cancelOrder(String orderId);
}
