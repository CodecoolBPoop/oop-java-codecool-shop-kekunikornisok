package com.codecool.shop.dao;

import java.util.List;

public interface ShippingAddressDao {

    void add(int id, String country, String city, String address, String zipCode);
    List<Integer> getUserId();
    void setTable(int id, String country, String city, String address, String zipCode);


}
