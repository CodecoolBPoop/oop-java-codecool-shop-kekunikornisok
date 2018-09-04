package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.ShoppingCartStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingCartDaoMem implements ShoppingCartDao {

    private List<ShoppingCart> data = new ArrayList<>();
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
    public void add(int userId, Date time, ShoppingCartStatus status) {
        data.add(new ShoppingCart(data.size() + 1, userId, time, status));
    }

    @Override
    public ShoppingCart find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public ShoppingCart find(String name) {
        return null;
    }

    @Override
    public ShoppingCart findActiveCart() {
        return data.stream().filter(t -> t.getStatus().equals(ShoppingCartStatus.IN_CART)).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<ShoppingCart> getAll() {
        return data;
    }

}
