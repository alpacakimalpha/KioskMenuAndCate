package dev.qf.client;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import common.OrderItem;
import common.Order;

public class OrderDetailView extends JDialog {
    private OwnerMainUI ownerMainUI;
    private final String orderId;

    public OrderDetailView(String orderId, OwnerMainUI ownerMainUI) {
        this.orderId = orderId;
        this.ownerMainUI = ownerMainUI;

        setTitle("주문번호 " + orderId);
        setModal(true);
        setSize(280, 440);
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
        URL imgUrl = getClass().getResource("/americano.jpg");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            Image scaledImg = icon.getImage().getScaledInstance(120, 170, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(imageLabel);
        } else {
            JLabel errorLabel = new JLabel("이미지를 찾을 수 없습니다.");
            errorLabel.setForeground(Color.RED);
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(errorLabel);
        }

        Order order = getOrderDetail(orderId);
        OrderItem item = order.getItems().isEmpty() ?
                new OrderItem("알 수 없음", 0, "알 수 없음") :
                order.getItems().get(0);

        JLabel menuLabel = new JLabel(item.name() + " " + item.quantity() + "개");
        menuLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(menuLabel);

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 2, 2));
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(200, 60));
        infoPanel.add(new JLabel("사이즈 :"));
        infoPanel.add(new JLabel("regular"));
        infoPanel.add(new JLabel("옵션 :"));
        infoPanel.add(new JLabel(item.option()));
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

    private Order getOrderDetail(String orderId) {
        // TODO: DB 연동 시 더미 데이터 제거
            for (Order order : ownerMainUI.getOrderService().getOrderList()) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        // 없으면 빈 Order 반환
        return new Order(orderId, "알 수 없음", "알 수 없음");
    }
}
