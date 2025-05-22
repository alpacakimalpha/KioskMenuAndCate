package dev.qf.client;

import common.Order;
import common.OrderItem;
import common.OrderStatus;
import common.OrderService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientOrderService implements OrderService {
    private final List<Order> orders = new ArrayList<>();

    public ClientOrderService() {
        // 더미 데이터
        List<OrderItem> items1 = new ArrayList<>();
        items1.add(new OrderItem("아메리카노", 1, "ICE"));
        orders.add(new Order(1, "KIOSK-001", LocalDateTime.now(), OrderStatus.대기중, items1));

        List<OrderItem> items2 = new ArrayList<>();
        items2.add(new OrderItem("카페라떼", 2, "HOT"));
        orders.add(new Order(2, "KIOSK-001", LocalDateTime.now(), OrderStatus.대기중, items2));
    }

    @Override
    public List<Order> getOrderList() {
        return orders;
    }

    @Override
    public void acceptOrder(int orderId) {
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.orderId() == orderId) {
                orders.set(i, order.withStatus(OrderStatus.수락됨));
            }
        }
    }

    @Override
    public void cancelOrder(int orderId) {
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.orderId() == orderId) {
                orders.set(i, order.withStatus(OrderStatus.취소됨));
            }
        }
    }
}
