package com.codecool.shop.model;

public class ShoppingCartProduct {
    private int shoppingCartId;
    private int productId;
    private int amount;

    public ShoppingCartProduct(int shoppingCartId, int productId, int amount) {
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
