package com.codecool.shop.dao;

import java.util.List;
import java.util.Map;

public interface ShoppingCartProductDao {

    int addProductToShoppingCart(int shoppingCartId, int productId);

    int removeProductToShoppingCart(int shoppingCartId, int productId);

    int getProductAmountInCart(int shoppingCartId);

    float getTotalPriceInCart(int shoppingCartId);

    List<Map<String, Object>> getOrderHistory(int shoppingCartId);

    List<Map<String, Object>> getShoppingCartProducts(int shoppingCartId);
}
