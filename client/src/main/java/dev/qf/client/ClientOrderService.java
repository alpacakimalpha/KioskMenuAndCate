package dev.qf.client;

import common.Order;
import common.OrderItem;
import common.OrderService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientOrderService implements OrderService {
    private final List<Order> orders = new ArrayList<>();

    public ClientOrderService() {
        // 더미 데이터 생성 (common.Order 사용)
        List<OrderItem> items1 = new ArrayList<>();
        items1.add(new OrderItem("아메리카노", 1, "ICE"));
        orders.add(new Order("1", new Date().toString(), "대기중", items1));

        List<OrderItem> items2 = new ArrayList<>();
        items2.add(new OrderItem("카페라떼", 2, "HOT"));
        orders.add(new Order("2", new Date().toString(), "대기중", items2));
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
