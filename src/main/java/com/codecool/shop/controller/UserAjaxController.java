package com.codecool.shop.controller;

import com.codecool.shop.dao.ShippingAddressDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.ShippingAddressDaoJDBC;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import com.codecool.shop.model.ShippingAddress;
import com.codecool.shop.model.User;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/handle-user"})
public class UserAjaxController extends HttpServlet {
    private UserDao userHandler = UserDaoJDBC.getInstance();
    private ShippingAddressDao shippingAddressHandler = ShippingAddressDaoJDBC.getInstance();

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
                Map<String, String> newData = new HashMap<>();

                session.setAttribute("userId", null);

                newData.put("alertColor", "success");
                newData.put("alertMessage", "You logged out successfully!");

                String json = new Gson().toJson(newData);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> newData = new HashMap<>();

        if (req.getParameter("event").equals("register")) {
            if (userHandler.validRegister(req.getParameter("userEmail"),
                    req.getParameter("userPassword"),
                    req.getParameter("userPasswordConfirm"))) {
                userHandler.add(req.getParameter("userEmail"), req.getParameter("userPassword"));
                newData.put("alertColor", "success");
                newData.put("alertMessage", "You registered successfully!");
            } else {
                newData.put("alertColor", "danger");
                newData.put("alertMessage", "Email is already in use or your passwords do not match!");
            }
        } else if (req.getParameter("event").equals("login")) {
            if (userHandler.validLogin(req.getParameter("userEmail"), req.getParameter("userPassword"))) {
                User user = userHandler.find(req.getParameter("userEmail"));

                req.getSession().setAttribute("userId", user.getId());

                newData.put("userId", Integer.toString(user.getId()));
                newData.put("userName", user.getFirstName());
                newData.put("alertColor", "success");
                newData.put("alertMessage", "You logged in successfully!");
            } else {
                newData.put("userId", null);
                newData.put("alertColor", "danger");
                newData.put("alertMessage", "Incorrect email or password!");
            }
        }
        else if (req.getParameter("event").equals("billing")) {
            userHandler.setTable((Integer)req.getSession(false).getAttribute("userId"),
                                req.getParameter("firstName"),
                                req.getParameter("lastName"),
                                req.getParameter("country"),
                                req.getParameter("city"),
                                req.getParameter("address"),
                                req.getParameter("zipCode"));
            newData.put("alertColor", "success");
            newData.put("alertMessage", "Save billing address!");
        }
        else if (req.getParameter("event").equals("shipping")) {
            if (shippingAddressHandler.getUserId().contains((Integer)req.getSession(false).getAttribute("userId"))) {

                shippingAddressHandler.setTable((Integer) req.getSession(false).getAttribute("userId"),
                        req.getParameter("country"),
                        req.getParameter("city"),
                        req.getParameter("address"),
                        req.getParameter("zipCode"));
                newData.put("alertColor", "success");
                newData.put("alertMessage", "Save billing address!");
            }else {
                shippingAddressHandler.add((Integer) req.getSession(false).getAttribute("userId"),
                        req.getParameter("country"),
                        req.getParameter("city"),
                        req.getParameter("address"),
                        req.getParameter("zipCode")
                );
            }
        }
        String json = new Gson().toJson(newData);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);

    }

}
