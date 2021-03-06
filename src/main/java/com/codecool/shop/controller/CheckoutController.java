package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    private ProductCategoryDao productCategory = ProductCategoryDaoJDBC.getInstance();
    private SupplierDao supplier = SupplierDaoJDBC.getInstance();
    private ShoppingCartDao shoppingCart = ShoppingCartDaoJDBC.getInstance();
    private ShoppingCartProductDao shoppingCartProduct = ShoppingCartProductDaoJDBC.getInstance();
    private UserDao userHandler = UserDaoJDBC.getInstance();
    private ShippingAddressDao shippingAddress = ShippingAddressDaoJDBC.getInstance();

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
                User user = userHandler.find((Integer) session.getAttribute("userId"));
                int userId = (Integer) session.getAttribute("userId");
                int activeCartId = shoppingCart.findActiveCartForUser(userId).getId();

                context.setVariable("users", user);
                context.setVariable("shippingAddresses", shippingAddress.find((Integer) session.getAttribute("userId")));

                context.setVariable("userId", userId);
                context.setVariable("products", shoppingCartProduct.getShoppingCartProducts(activeCartId));
                context.setVariable("totalItemNumber", shoppingCartProduct.getProductAmountInCart(activeCartId));
                context.setVariable("totalPrice", shoppingCartProduct.getTotalPriceInCart(activeCartId));
                context.setVariable("category", productCategory.getAll());
                context.setVariable("supplier", supplier.getAll());

                engine.process("cart/checkout.html", context, resp.getWriter());
            }
        }
    }
}
