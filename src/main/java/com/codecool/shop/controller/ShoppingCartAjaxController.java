package com.codecool.shop.controller;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.ShoppingCartProductDao;
import com.codecool.shop.dao.implementation.ShoppingCartDaoJDBC;
import com.codecool.shop.dao.implementation.ShoppingCartProductDaoJDBC;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/change-quantity"})
public class ShoppingCartAjaxController extends HttpServlet {
    private ShoppingCartDao shoppingCart = ShoppingCartDaoJDBC.getInstance();
    private ShoppingCartProductDao shoppingCartProduct = ShoppingCartProductDaoJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            session = req.getSession(true);
            session.setAttribute("userId", null);
            resp.sendRedirect("/");
        } else {
            if (session.getAttribute("userId") == null) {
                resp.sendRedirect("/");
            } else {
                Map<String, Integer> newData = new HashMap<>();

                newData.put("totalItemsInCart", shoppingCartProduct.getProductAmountInCart(shoppingCart.findActiveCart().getId()));
                String json = new Gson().toJson(newData);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            session = req.getSession(true);
            session.setAttribute("userId", null);
            resp.sendRedirect("/");
        } else {
            if (session.getAttribute("userId") == null) {
                resp.sendRedirect("/");
            } else {
                Map<String, Integer> newData = new HashMap<>();

                int productId = Integer.parseInt(req.getParameter("id"));
                int activeCartId = shoppingCart.findActiveCart().getId();
                int newQuantity = 0;

                if (req.getParameter("quantity").equals("decrease")) {
                    newQuantity = shoppingCartProduct.removeProductToShoppingCart(activeCartId, productId);
                } else if (req.getParameter("quantity").equals("increase")) {
                    newQuantity = shoppingCartProduct.addProductToShoppingCart(activeCartId, productId);
                }

                int newTotalItems = shoppingCartProduct.getProductAmountInCart(activeCartId);
                int newTotalPrice = Math.round(shoppingCartProduct.getTotalPriceInCart(activeCartId) * 100) / 100;

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
    }

}
