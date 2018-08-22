package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private ProductDao productDataStore = ProductDaoJDBC.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
    private SupplierDao supplierDataStore = SupplierDaoJDBC.getInstance();
    private ShoppingCartDao shoppingCart = ShoppingCartDaoMem.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String categoryNameFromUrl = req.getParameter("category");
        String supplierNameFromUrl = req.getParameter("supplier");

        HttpSession session = req.getSession();
        context.setVariable("username", session.getAttribute("username"));
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDataStore.getAll());
        context.setVariable("category_name", getCategoryImg(categoryNameFromUrl));
        context.setVariable("products", getProducts(categoryNameFromUrl, supplierNameFromUrl));

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String categoryNameFromUrl = req.getParameter("category");
        String supplierNameFromUrl = req.getParameter("supplier");

        addProductToShoppingCart(Integer.parseInt(req.getParameter("product")), productDataStore, shoppingCart);

        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDataStore.getAll());
        context.setVariable("category_name", getCategoryImg(categoryNameFromUrl));
        context.setVariable("products", getProducts(categoryNameFromUrl, supplierNameFromUrl));

        engine.process("product/index.html", context, resp.getWriter());

    }

    private List<Product> getProducts(String categoryNameFromUrl, String supplierNameFromUrl) {
        if (categoryNameFromUrl != null) {
            return productDataStore.getBy(productCategoryDataStore.find(categoryNameFromUrl));
        } else if (supplierNameFromUrl != null) {
            return productDataStore.getBy(supplierDataStore.find(supplierNameFromUrl));
        }

        return productDataStore.getAll();
    }

    private String getCategoryImg (String categoryNameFromUrl) {
        if (categoryNameFromUrl != null) {
            return categoryNameFromUrl;
        } else {
            return "Cloud";
        }
    }

    private void addProductToShoppingCart(int productId, ProductDao productStore, ShoppingCartDao shoppingCart) {
        for (Product item : productStore.getAll()) {
            if (item.getId() == productId) {
                shoppingCart.add(item);
            }
        }
    }
}
