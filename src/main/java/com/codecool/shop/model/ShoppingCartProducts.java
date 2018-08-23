package com.codecool.shop.model;

public class ShoppingCartProducts {

    private int shoppingCartId;
    private int productId;
    private int amount;


    public ShoppingCartProducts(int shoppingCardId, int productId, int amount) {
        this.shoppingCartId = shoppingCardId;
        this.productId = productId;
        this.amount = amount;
    }

    public int getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
