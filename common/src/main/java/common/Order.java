package common;

import java.time.LocalDateTime;

public record Order(
        int orderId,
        String customer,
        LocalDateTime orderTime,
        OrderStatus status,
        Cart cart
) {
    public static final Order EMPTY = new Order(-1, "UNKNOWN", LocalDateTime.MIN, OrderStatus.UNKNOWN, Cart.EMPTY);

    public Order(int orderId, String customer, LocalDateTime orderTime, OrderStatus status) {
        this(orderId, customer, orderTime, status, new Cart());
    }

    public Order withStatus(OrderStatus newStatus) {
        return new Order(this.orderId, this.customer, this.orderTime, newStatus, this.cart);
    }
}
