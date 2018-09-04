package com.codecool.shop.dao;


import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.ShoppingCartStatus;

import java.util.Date;
import java.util.List;

public interface ShoppingCartDao {

    void add(int userId, Date time, ShoppingCartStatus status);
    ShoppingCart find(int id);
    ShoppingCart find(String name);
    ShoppingCart findActiveCart();

    void remove(int id);

    List<ShoppingCart> getAll();

}
