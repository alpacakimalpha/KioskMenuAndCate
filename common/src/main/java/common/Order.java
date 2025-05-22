package common;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public record Order(
        String orderId,
        LocalDateTime orderTime,
        OrderStatus status,
        List<OrderItem> items
) {
    public static final Order EMPTY = new Order("EMPTY", LocalDateTime.MIN, OrderStatus.대기중, List.of());

    public Order(String orderId, LocalDateTime orderTime, OrderStatus status) {
        this(orderId, orderTime, status, new ArrayList<>());
    }

    public Order withStatus(OrderStatus newStatus) {
        return new Order(this.orderId, this.orderTime, newStatus, this.items);
    }
}
