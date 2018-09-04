package com.codecool.shop.model;

import java.util.Date;

public class ShoppingCart {

    private int id;
    private int userId;
    private Date time;
    private ShoppingCartStatus status;

    public ShoppingCart(int id, int userId, java.sql.Date time, ShoppingCartStatus status) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ShoppingCartStatus getStatus() {
        return status;
    }

    public void setStatus(ShoppingCartStatus status) {
        this.status = status;
    }
}
