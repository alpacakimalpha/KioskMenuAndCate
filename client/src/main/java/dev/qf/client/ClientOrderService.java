package dev.qf.client;

import java.util.ArrayList;
import java.util.List;

public class ClientOrderService implements OrderService {
    private List<Order> orders = new ArrayList<>();

    public ClientOrderService() {
        // 더미 데이터 초기화
        Order order1 = new Order("1", "2025-05-19 14:30", "대기중");
        order1.addItem(new OrderItem("아메리카노", 1, "ICE"));
        orders.add(order1);

        Order order2 = new Order("2", "2025-05-19 15:00", "대기중");
        order2.addItem(new OrderItem("카페라떼", 2, "HOT"));
        orders.add(order2);
    }

    @Override
    public List<Order> getOrderList() {
        return orders;
    }

    @Override
    public void acceptOrder(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus("수락됨");
            }
        }
    }

    @Override
    public void cancelOrder(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus("취소됨");
            }
        }
    }
}
