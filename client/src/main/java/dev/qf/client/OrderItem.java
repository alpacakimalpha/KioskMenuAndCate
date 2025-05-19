package dev.qf.client;

public class OrderItem {
    private String name;
    private int quantity;
    private String option;

    public OrderItem(String name, int quantity, String option) {
        this.name = name;
        this.quantity = quantity;
        this.option = option;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public String getOption() { return option; }
}
