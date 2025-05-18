package dev.qf.client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OwnerMainUI extends JFrame {
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private OrderService orderService;

    public OwnerMainUI() {
        initializeUI();
        setupOrderService();
    }

    private void initializeUI() {
        setTitle("점주 주문 관리 시스템");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = {"주문번호", "시간", "상태"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);

        JPanel buttonPanel = new JPanel();
        refreshButton = new JButton("주문 새로고침");
        refreshButton.addActionListener(this::handleRefresh);
        buttonPanel.add(refreshButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(orderTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = orderTable.rowAtPoint(evt.getPoint());
                if (row != -1 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                    String orderId = (String) orderTable.getValueAt(row, 0);
                    OrderDetailView detailView = new OrderDetailView(orderId, OwnerMainUI.this);
                    detailView.setVisible(true);
                }
            }
        });
    }

    private void setupOrderService() {
        this.orderService = new ClientOrderService();
        loadOrderData();
    }

    public void loadOrderData() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            List<Order> orders;
            @Override
            protected Void doInBackground() {
                orders = orderService.getOrderList();
                return null;
            }

            @Override
            protected void done() {
                updateOrderTable(orders);
            }
        };
        worker.execute();
    }

    private void updateOrderTable(List<Order> orders) {
        tableModel.setRowCount(0);
        for (Order order : orders) {
            Object[] rowData = {order.getOrderId(), order.getOrderTime(), order.getStatus()};
            tableModel.addRow(rowData);
        }
    }

    private void handleRefresh(ActionEvent e) {
        loadOrderData();
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OwnerMainUI app = new OwnerMainUI();
            app.setVisible(true);
        });
    }
}
