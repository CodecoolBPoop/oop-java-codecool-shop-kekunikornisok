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
    private ShoppingCartDao shoppingCart = ShoppingCartDaoMem.getInstance();
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

        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDataStore.getAll());
        renderThePage(categoryIdFromUrl,supplierIdFromUrl,context);

        System.out.println(categoryIdFromUrl);
        System.out.println(supplierIdFromUrl);


        engine.process("product/index.html", context, resp.getWriter());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        for (Product item : productDataStore.getAll()) {
            String id = String.valueOf(item.getId());
            if (id.equals(req.getParameter("product"))) {
                shoppingCart.add(item);
            }
        }
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String categoryIdFromUrl = req.getParameter("category");
        String supplierIdFromUrl = req.getParameter("supplier");

        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDataStore.getAll());
        renderThePage(categoryIdFromUrl,supplierIdFromUrl,context);

        engine.process("product/index.html", context, resp.getWriter());
        System.out.println(categoryIdFromUrl);
        System.out.println(supplierIdFromUrl);

    }


private void renderThePage(String categoryIdFromUrl, String supplierIdFromUrl, WebContext context){
    if(categoryIdFromUrl != null){
        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(Integer.parseInt(categoryIdFromUrl))));
    } else if (supplierIdFromUrl != null){
        context.setVariable("products", productDataStore.getBy(supplierDataStore.find(Integer.parseInt(supplierIdFromUrl))));
    }
    else {
        context.setVariable("products", productDataStore.getAll());
    }
    }
}
