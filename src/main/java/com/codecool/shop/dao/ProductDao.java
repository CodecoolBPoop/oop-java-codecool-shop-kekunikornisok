package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

public interface ProductDao {

    void add(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier);
    Product find(int id);
    Product find(String name);

    void remove(int id);

    List<Product> getAll();
    List<Product> getBySupplier(int supplierId);
    List<Product> getByProductCategory(int productCategoryId);

}
