package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

public class ShoppingCart {

    private int id;
    private int userId;
    private Date time;
    private String status;
    private List<Product> productsInCart = new ArrayList<>();

    public ShoppingCart(int id, int userId, java.sql.Date time, String status) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addProductToCartList(Product product) { productsInCart.add(product); }

    public Product findProductFromCartListById(int id){
        return productsInCart.stream().filter(t -> t.getId() == id).findFirst().orElse(null);}

    public Product findProductFromCartListByName(String name) {
        return productsInCart.stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null);
    }

    public LinkedHashSet<Product> getAllFromCurrentlyCart(){return new LinkedHashSet<>(productsInCart); }
}
