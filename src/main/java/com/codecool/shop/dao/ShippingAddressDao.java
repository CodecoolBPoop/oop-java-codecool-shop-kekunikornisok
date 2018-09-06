package com.codecool.shop.dao;

import com.codecool.shop.model.ShippingAddress;

import java.util.List;

public interface ShippingAddressDao {

    void add(int userId, String country, String city, String address, String zipCode);
    List<Integer> getUserId();
    void setTable(int userId, String country, String city, String address, String zipCode);
    List<ShippingAddress> find(int userId);

}
