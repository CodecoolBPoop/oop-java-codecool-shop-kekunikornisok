package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private static final JDBCController controller = JDBCController.getInstance();
    private static ProductDaoJDBC instance = null;

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    private List<Product> executeQueryWithReturnValue(String query, List<Object> parameters) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> resultList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product data = new Product(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("currency_string"),
                        resultSet.getString("description"),
                        ProductCategoryDaoJDBC.getInstance().find(resultSet.getInt("product_category_id")),
                        SupplierDaoJDBC.getInstance().find(resultSet.getInt("supplier_id")));
                resultList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return resultList;
    }

    @Override
    public void add(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        controller.executeQuery(
        "INSERT INTO product (id, name, description, default_price, currency_string, supplier_id, product_category_id) " +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);",
            Arrays.asList(name, description, defaultPrice, currencyString, supplier.getId(), productCategory.getId())
        );
    }

    @Override
    public Product find(int id) {
        List<Product> products = executeQueryWithReturnValue(
        "SELECT * FROM product WHERE id = ?;",
            Collections.singletonList(id));

        return (products.size() != 0) ? products.get(0) : null;
    }

    @Override
    public Product find(String name) {
        List<Product> products = executeQueryWithReturnValue(
        "SELECT * FROM product WHERE name LIKE ?;",
            Collections.singletonList(name));

        return (products.size() != 0) ? products.get(0) : null;
    }

    @Override
    public void remove(int id) {
        controller.executeQuery(
        "DELETE FROM product WHERE id = ?;",
            Collections.singletonList(id));
    }

    @Override
    public List<Product> getAll() {
        return executeQueryWithReturnValue(
        "SELECT * FROM product;",
            Collections.emptyList());
    }

    @Override
    public List<Product> getBySupplier(int supplierId) {
        return executeQueryWithReturnValue(
        "SELECT * FROM product WHERE supplier_id = ?;",
            Collections.singletonList(supplierId));
    }

    @Override
    public List<Product> getByProductCategory(int productCategoryId) {
        return executeQueryWithReturnValue(
        "SELECT * FROM product WHERE product_category_id = ?;",
            Collections.singletonList(productCategoryId));
    }

}
