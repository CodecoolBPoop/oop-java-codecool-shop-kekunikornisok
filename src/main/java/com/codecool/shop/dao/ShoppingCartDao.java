package com.codecool.shop.dao;

import com.codecool.shop.model.*;
import java.util.List;

public interface ShoppingCartDao {

    void add(Product product);
    Product find(int id);
    void remove(int id);

    List<Product> getAll();

}
