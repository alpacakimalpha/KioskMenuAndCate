package common;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public record Order(
        int orderId,
        String customer,
        LocalDateTime orderTime,
        OrderStatus status,
        List<OrderItem> items
) {
    public static final Order EMPTY = new Order(-1, "UNKNOWN", LocalDateTime.MIN, OrderStatus.대기중, List.of());

    public Order(int orderId, String customer, LocalDateTime orderTime, OrderStatus status) {
        this(orderId, customer, orderTime, status, new ArrayList<>());
    }

    public Order withStatus(OrderStatus newStatus) {
        return new Order(this.orderId, this.customer, this.orderTime, newStatus, this.items);
    }
}
