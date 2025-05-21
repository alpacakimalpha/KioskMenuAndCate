package common;

import java.util.List;
import java.util.ArrayList;

public record Order(
        String orderId,
        String orderTime,
        String status,
        List<OrderItem> items
) {
    public static final Order EMPTY = new Order("EMPTY", "EMPTY", "EMPTY", List.of());

    public Order(String orderId, String orderTime, String status) {
        this(orderId, orderTime, status, new ArrayList<>());
    }

    public Order withStatus(String newStatus) {
        return new Order(this.orderId, this.orderTime, newStatus, this.items);
    }
}
