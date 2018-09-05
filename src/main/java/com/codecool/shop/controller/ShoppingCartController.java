package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
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
    private ProductDao productDataStore = ProductDaoJDBC.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
    private ShoppingCartDao shoppingCart = ShoppingCartDaoJDBC.getInstance();
    private SupplierDao supplierDataStore = SupplierDaoJDBC.getInstance();
    private ShoppingCartProductDao shoppingCartProduct = ShoppingCartProductDaoJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
                int activeCartId = shoppingCart.findActiveCart().getId();
                context.setVariable("userId", session.getAttribute("userId"));
                context.setVariable("products", shoppingCartProduct.getShoppingCartProducts(activeCartId));
                context.setVariable("totalItemNumber", shoppingCartProduct.getProductAmountInCart(activeCartId));
                context.setVariable("totalPrice", shoppingCartProduct.getTotalPriceInCart(activeCartId));
                context.setVariable("category", productCategoryDataStore.getAll());
                context.setVariable("supplier", supplierDataStore.getAll());

                engine.process("cart/shopping_cart.html", context, resp.getWriter());
            }
        }
    }

}

