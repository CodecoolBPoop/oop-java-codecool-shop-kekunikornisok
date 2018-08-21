package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.Product;

import java.util.Set;


public class ShoppingCartDaoJDBC implements ShoppingCartDao {
    private static final JDBCController controller = JDBCController.getInstance();

    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public Product find(String name) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Set<Product> getAll() {
        return null;
    }
}
