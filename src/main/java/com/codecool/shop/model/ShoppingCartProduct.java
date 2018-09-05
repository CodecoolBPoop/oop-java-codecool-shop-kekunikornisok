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

    public int getShoppingCartId() {
        return shoppingCartId;
    }

    public int getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }
}
