package dev.qf.client;

import common.Cart;
import common.OrderItem;

public class CartController {
    private final Cart cart;

    public CartController(Cart cart) {
        this.cart = cart;
    }

    public void addItemToCart(OrderItem item) {
        cart.addItem(item);
    }
}