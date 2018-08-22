package com.codecool.shop.controller;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.UserDaoJBDC;
import com.codecool.shop.model.User;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/handle-user"})
public class UserAjaxController extends HttpServlet {
    private UserDao userHandler = UserDaoJBDC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Integer> newData = new HashMap<>();

        newData.put("totalItemsInCart", shoppingCart.getSize());
        String json = new Gson().toJson(newData);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> newData = new HashMap<>();

        if (req.getParameter("event").equals("register")) {
            if (userHandler.validEmail(req.getParameter("email_register"))) {
                userHandler.add(req.getParameter("email_register"), req.getParameter("password_register"),
                        null, null, null, null, null, null, false);
                newData.put("alertColor", "success");
                newData.put("alertMessage", "You registered successfully!");
            } else {
                newData.put("alertColor", "danger");
                newData.put("alertMessage", "Incorrect email or password!");
            }
        } else if (req.getParameter("event").equals("login")) {
            if (userHandler.validLogin(req.getParameter("email_login"), req.getParameter("password_login"))) {
                User user = userHandler.find(req.getParameter("email_login"));
                newData.put("userId", Integer.toString(user.getId()));
                newData.put("userName", user.getFirstName());
                newData.put("alertColor", "success");
                newData.put("alertMessage", "You registered successfully!");
            } else {
                newData.put("alertColor", "danger");
                newData.put("alertMessage", "Incorrect email or password!");
            }
        }

        String json = new Gson().toJson(newData);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

}
