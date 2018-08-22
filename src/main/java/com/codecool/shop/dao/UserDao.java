package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User find(int id);
    User find (String email);
    void remove(int id);
    List<User> getAll();
    boolean validRegister(String email);
    boolean validlogin(String email, String password);
}
