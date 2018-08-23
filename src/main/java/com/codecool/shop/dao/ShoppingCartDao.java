package com.codecool.shop.dao;

import com.codecool.shop.model.*;
import java.util.Set;

public interface ShoppingCartDao {

    void add(Product product, ShoppingCart shoppingCart);
    Product find(int id, ShoppingCart shoppingCart);
    Product find(String name, ShoppingCart shoppingCart);

    void remove(int id);

    Set<Product> getAll(ShoppingCart shoppingCart);

}
