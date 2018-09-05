package com.codecool.shop.dao;


import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.ShoppingCartStatus;

import java.util.Date;
import java.util.List;

public interface ShoppingCartDao {

    void add(int userId, Date time);
    ShoppingCart find(int id);
    ShoppingCart findActiveCartForUser(int userId);

    void changeCartStatus(int userId, ShoppingCartStatus statusFrom, ShoppingCartStatus statusTo);

    void remove(int id);

    List<ShoppingCart> getAll();

}
