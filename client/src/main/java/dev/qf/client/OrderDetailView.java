package dev.qf.client;

import javax.swing.*;
import java.awt.*;
import common.OrderItem;
import common.Order;
import common.Cart;

public class OrderDetailView extends JDialog {
    private OwnerMainUI ownerMainUI;
    private final int orderId;

    public OrderDetailView(int orderId, OwnerMainUI ownerMainUI) {
        this.orderId = orderId;
        this.ownerMainUI = ownerMainUI;

        setTitle("주문번호 " + orderId);
        setModal(true);
        setSize(320, 440);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        JLabel orderNumLabel = new JLabel("주문번호 " + orderId, SwingConstants.CENTER);
        orderNumLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        orderNumLabel.setForeground(new Color(51, 153, 255));
        orderNumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(orderNumLabel);

        contentPanel.add(Box.createVerticalStrut(8));

        Order order = getOrderDetail(orderId);
        Cart cart = order.cart();


        if (cart == null || cart.items().isEmpty()) {
            JLabel emptyLabel = new JLabel("주문 항목이 없습니다.");
            emptyLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(emptyLabel);
        } else {
            for (OrderItem item : cart.items()) {
                JLabel menuLabel = new JLabel(item.name() + " " + item.quantity() + "개");
                menuLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
                menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(menuLabel);

                String optionText;
                if (item.name().contains("케이크")) {
                    optionText = "(옵션: " + item.option() + ")";
                } else {
                    optionText = "(regular, " + item.option() + ")";
                }
                JLabel optionLabel = new JLabel(optionText);
                optionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
                optionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(optionLabel);

                contentPanel.add(Box.createVerticalStrut(4));
            }
        }

        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 2, 2));
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(200, 30));
        infoPanel.add(new JLabel("포장 :"));
        infoPanel.add(new JLabel("아니요"));
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(infoPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton cancelBtn = new JButton("주문 취소");
        cancelBtn.setBackground(new Color(204, 0, 0));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        cancelBtn.setPreferredSize(new Dimension(110, 40));

        JButton acceptBtn = new JButton("주문 수락");
        acceptBtn.setBackground(new Color(51, 153, 255));
        acceptBtn.setForeground(Color.WHITE);
        acceptBtn.setFocusPainted(false);
        acceptBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        acceptBtn.setPreferredSize(new Dimension(110, 40));

        cancelBtn.addActionListener(e -> {
            ownerMainUI.getOrderService().cancelOrder(orderId);
            ownerMainUI.loadOrderData();
            JOptionPane.showMessageDialog(this, "주문이 취소되었습니다.");
            dispose();
        });

        acceptBtn.addActionListener(e -> {
            ownerMainUI.getOrderService().acceptOrder(orderId);
            ownerMainUI.loadOrderData();
            JOptionPane.showMessageDialog(this, "주문이 수락되었습니다.");
            dispose();
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(acceptBtn);

        contentPanel.add(Box.createVerticalStrut(18));
        contentPanel.add(buttonPanel);

        add(contentPanel);
    }

    private Order getOrderDetail(int orderId) {
        for (Order order : ownerMainUI.getOrderService().getOrderList()) {
            if (order.orderId() == orderId) {
                return order;
            }
        }
        return Order.EMPTY;
    }
}
