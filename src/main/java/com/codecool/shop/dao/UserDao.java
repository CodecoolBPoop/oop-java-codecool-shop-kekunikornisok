package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.List;

public interface UserDao {
    void add(String emailAddress, String password);
    void add(String emailAddress, String password, String firstName, String lastName, String country, String city, String address, String zipCode, boolean isShippingSame);
    void setTable(int id, String firstName, String lastName, String country, String city, String address, String zipCode);
    User find(int id);
    User find (String email);
    void remove(int id);
    List<User> getAll(int id);
    List<String> getEmails();
    boolean validRegister(String email, String password, String passwordConfirm);
    boolean validLogin(String email, String password);
}
