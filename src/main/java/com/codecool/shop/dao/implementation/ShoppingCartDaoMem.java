package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;

import java.util.*;

public class ShoppingCartDaoMem implements ShoppingCartDao {

    private List<Product> productsInCart = new ArrayList<>();
    private float totalPrice = 0;
    private static ShoppingCartDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ShoppingCartDaoMem() {
    }

    public static ShoppingCartDaoMem getInstance() {
        if (instance == null) {
            instance = new ShoppingCartDaoMem();
        }
        return instance;
    }


    @Override
    public void add(Product product) {
        productsInCart.add(product);
        totalPrice += product.getDefaultPrice();
    }

    @Override
    public Product find(int id) {
        return productsInCart.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        productsInCart.remove(find(id));
    }

    @Override
    public LinkedHashSet<Product> getAll() {
        return new LinkedHashSet<>(productsInCart);
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public int getQuantityById(int id) {
        return (int) productsInCart.stream().filter(t -> t.getId() == id).count();
    }

}
