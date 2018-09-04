package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {

    private List<Product> data = new ArrayList<>();
    private static ProductDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMem() {
    }

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        data.add(new Product(data.size() + 1, name, defaultPrice, currencyString, description, productCategory, supplier));
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Product find(String name) {
        return data.stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        return data;
    }

    @Override
    public List<Product> getBySupplier(int supplierId) {
        return data.stream().filter(t -> t.getSupplier().getId() == supplierId).collect(Collectors.toList());
    }

    @Override
    public List<Product> getByProductCategory(int productCategoryId) {
        return data.stream().filter(t -> t.getProductCategory().getId() == productCategoryId).collect(Collectors.toList());
    }
}
