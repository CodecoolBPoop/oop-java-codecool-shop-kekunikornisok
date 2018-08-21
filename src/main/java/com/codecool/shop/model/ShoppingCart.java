package com.codecool.shop.model;

import java.util.Date;

public class ShoppingCart {

    private int userId;
    private Date time;
    private String status;

    public ShoppingCart(int userId, java.sql.Date time, String status) {
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
}
