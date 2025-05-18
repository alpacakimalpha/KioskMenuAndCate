package dev.qf.client;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class OrderDetailView extends JDialog {
    private OwnerMainUI ownerMainUI;
    private String orderId;

    public OrderDetailView(String orderId, OwnerMainUI ownerMainUI) {
        this.orderId = orderId;
        this.ownerMainUI = ownerMainUI;

        setTitle("주문번호 " + orderId);
        setModal(true);
        setSize(340, 560); // 높이 충분히 크게!
        setLocationRelativeTo(null);
        setResizable(false);

        // BorderLayout 사용
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel orderLabel = new JLabel("주문번호 " + orderId, SwingConstants.CENTER);
        orderLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        orderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(orderLabel);

        // 이미지
        URL imgUrl = getClass().getResource("/americano.jpg");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            Image scaledImg = icon.getImage().getScaledInstance(150, 180, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(imageLabel);
        } else {
            JLabel errorLabel = new JLabel("이미지를 찾을 수 없습니다.");
            errorLabel.setForeground(Color.RED);
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(errorLabel);
        }

        // 메뉴명, 수량
        Order order = getOrderDetail(orderId);
        JLabel menuLabel = new JLabel(order.getItems().get(0).getName() + " " +
                order.getItems().get(0).getQuantity() + "개");
        menuLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(menuLabel);

        // 상세 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 2, 5, 5));
        infoPanel.add(new JLabel("사이즈 :"));
        infoPanel.add(new JLabel("regular"));
        infoPanel.add(new JLabel("옵션 :"));
        infoPanel.add(new JLabel(order.getItems().get(0).getOption()));
        infoPanel.add(new JLabel("포장 :"));
        infoPanel.add(new JLabel("아니요"));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(infoPanel);

        // 스크롤 지원 (내용이 많을 때)
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);

        // 버튼 패널 (아래 고정)
        JPanel buttonPanel = new JPanel();
        JButton cancelBtn = new JButton("주문 취소");
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        JButton acceptBtn = new JButton("주문 수락");
        acceptBtn.setBackground(new Color(51, 153, 255));
        acceptBtn.setForeground(Color.WHITE);

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
        buttonPanel.add(acceptBtn);

        // BorderLayout으로 배치
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private Order getOrderDetail(String orderId) {
        for (Order order : ownerMainUI.getOrderService().getOrderList()) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        Order emptyOrder = new Order(orderId, "알 수 없음", "알 수 없음");
        emptyOrder.addItem(new OrderItem("알 수 없음", 0, "알 수 없음"));
        return emptyOrder;
    }
}
