package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/change-quantity"})
public class AjaxController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ShoppingCartDaoMem shoppingCart = ShoppingCartDaoMem.getInstance();
        Map<String, Integer> newData = new HashMap<>();

        newData.put("totalItemsInCart", shoppingCart.getSize());
        String json = new Gson().toJson(newData);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ShoppingCartDaoMem shoppingCart = ShoppingCartDaoMem.getInstance();
        Map<String, Integer> newData = new HashMap<>();

        int productId = Integer.parseInt(req.getParameter("id"));

        if (req.getParameter("quantity").equals("decrease")) {
            shoppingCart.remove(productId);
        } else if (req.getParameter("quantity").equals("increase")) {
            shoppingCart.add(shoppingCart.find(productId));
        }

        int newQuantity = (shoppingCart.getQuantityById(productId) != null) ? shoppingCart.getQuantityById(productId) : 0;
        int newTotalItems = shoppingCart.getSize();
        int newTotalPrice = Math.round(shoppingCart.getTotalPrice() * 100) / 100;

        newData.put("productId", productId);
        newData.put("newQuantity", newQuantity);
        newData.put("newTotalItems", newTotalItems);
        newData.put("newTotalPrice", newTotalPrice);
        String json = new Gson().toJson(newData);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

}
