package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/shopping-cart"})
public class ShoppingCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ShoppingCartDaoJDBC shoppingCartJDBC = ShoppingCartDaoJDBC.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        HttpSession session = req.getSession(false);
        if (session == null) {
            session = req.getSession(true);
            session.setAttribute("userId", null);
            resp.sendRedirect("/");
        } else {
            if (session.getAttribute("userId") == null) {
                resp.sendRedirect("/");
            } else {
                context.setVariable("userId", session.getAttribute("userId"));
                context.setVariable("products", shoppingCart.getAll());
                context.setVariable("shopping_cart", shoppingCartJDBC.findUserCartUserId((Integer) req.getSession().getAttribute("userId")));
                context.setVariable("category", productCategoryDataStore.getAll());
                context.setVariable("supplier", supplierDataStore.getAll());

                engine.process("cart/shopping_cart.html", context, resp.getWriter());
            }
        }
    }

}

