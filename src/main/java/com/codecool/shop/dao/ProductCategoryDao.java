package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {

    void add(String name, String description, String department);
    ProductCategory find(int id);
    ProductCategory find(String name);

    void remove(int id);

    List<ProductCategory> getAll();

}
