package com.codecool.shop.dao;

public interface ShoppingCartProductDao {

    void addProductToShoppingCart(int shoppingCartId, int productId);

    void removeProductToShoppingCart(int shoppingCartId, int productId);
}
