package com.codecool.shop.dao;

import com.codecool.shop.model.*;
import java.util.Set;

public interface ShoppingCartDao {

    void add(Product product);
    Product find(int id);
    Product find(String name);

    void remove(int id);

    Set<Product> getAll();

}
