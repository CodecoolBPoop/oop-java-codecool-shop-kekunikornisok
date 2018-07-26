package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    private SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariables(params);

        String categoryIdFromUrl = req.getParameter("category");
        String supplierIdFromUrl = req.getParameter("supplier");
        String categoryNameFromUrl = req.getParameter("value");


        getCategoryImg(categoryNameFromUrl, context);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDataStore.getAll());
        renderThePage(categoryIdFromUrl,supplierIdFromUrl,context);

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        ShoppingCartDao shoppingCart = ShoppingCartDaoMem.getInstance();
        String categoryIdFromUrl = req.getParameter("category");
        String supplierIdFromUrl = req.getParameter("supplier");
        String categoryNameFromUrl = req.getParameter("value");


        addProductToShoppingCart(req, productDataStore, shoppingCart);

        getCategoryImg(categoryNameFromUrl, context);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDataStore.getAll());
        renderThePage(categoryIdFromUrl,supplierIdFromUrl,context);

        engine.process("product/index.html", context, resp.getWriter());

    }


    private void renderThePage(String categoryIdFromUrl, String supplierIdFromUrl, WebContext context) {
        if (categoryIdFromUrl != null) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(Integer.parseInt(categoryIdFromUrl))));
        } else if (supplierIdFromUrl != null) {
            context.setVariable("products", productDataStore.getBy(supplierDataStore.find(Integer.parseInt(supplierIdFromUrl))));
        } else {
            context.setVariable("products", productDataStore.getAll());
        }
    }

    private void getCategoryImg (String categoryNameFromUrl, WebContext context){
        for (Product name : productDataStore.getAll()) {
            if (name.getProductCategory().getName().equals(categoryNameFromUrl)) {
                context.setVariable("categoryName", name.getProductCategory().getName());
                return;
            }
            else {
                context.setVariable("categoryName", "Cloud" );
            }
        }
    }
    private void addProductToShoppingCart(HttpServletRequest req, ProductDao productStore, ShoppingCartDao shoppingCart) {
        for (Product item : productStore.getAll()) {
            String id = String.valueOf(item.getId());
            if (id.equals(req.getParameter("product"))) {
                shoppingCart.add(item);
            }
        }
    }
}
