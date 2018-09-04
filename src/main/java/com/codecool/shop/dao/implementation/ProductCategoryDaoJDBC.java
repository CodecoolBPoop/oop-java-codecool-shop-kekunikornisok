package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    private static final JDBCController controller = JDBCController.getInstance();
    private static ProductCategoryDaoJDBC instance = null;

    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

    private List<ProductCategory> executeQueryWithReturnValue(String query, List<Object> parameters) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProductCategory> resultList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProductCategory data = new ProductCategory(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("department"));
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
    public void add(String name, String description, String department) {
        controller.executeQuery(
        "INSERT INTO product_category (id, name, description, department) " +
                "VALUES (DEFAULT, ?, ?, ?);",
            Arrays.asList(name, description, department));
    }

    @Override
    public ProductCategory find(int id) {
        List<ProductCategory> productCategories = executeQueryWithReturnValue(
        "SELECT * FROM product_category WHERE id = ?;",
            Collections.singletonList(id));

        return (productCategories.size() != 0) ? productCategories.get(0) : null;
    }

    @Override
    public ProductCategory find(String name) {
        List<ProductCategory> productCategories = executeQueryWithReturnValue(
        "SELECT * FROM product_category WHERE name LIKE ?;",
            Collections.singletonList(name));

        return (productCategories.size() != 0) ? productCategories.get(0) : null;
    }

    @Override
    public void remove(int id) {
        controller.executeQuery(
        "DELETE FROM product_category WHERE id = ?;",
            Collections.singletonList(id));
    }

    @Override
    public List<ProductCategory> getAll() {
        return executeQueryWithReturnValue(
        "SELECT * FROM product_category;",
            Collections.emptyList());
    }

}
