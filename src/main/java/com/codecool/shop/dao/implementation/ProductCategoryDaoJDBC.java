package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    private static final JDBCController controller = JDBCController.getInstance();

    public List<ProductCategory> executeQueryWithReturnValue(String query) {
        List<ProductCategory> resultList = new ArrayList<>();

        try (Connection connection = controller.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                ProductCategory data = new ProductCategory(resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("department"));
                resultList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public void add(ProductCategory category) {
        controller.executeQuery("INSERT INTO product_category (id, name, description, department) VALUES (DEFAULT, '" +
                                category.getName() + "', '" + category.getDescription() + "', '" + category.getDepartment() + "';");
    }

    @Override
    public ProductCategory find(int id) {
        return executeQueryWithReturnValue("SELECT * FROM product_category WHERE id = '" + id + "';").get(0);
    }

    @Override
    public ProductCategory find(String name) {
        return executeQueryWithReturnValue("SELECT * FROM product_category WHERE name LIKE '" + name + "';").get(0);
    }

    @Override
    public void remove(int id) {
        controller.executeQuery("DELETE FROM product_category WHERE id = '" + id + "';");
    }

    @Override
    public List<ProductCategory> getAll() {
        return executeQueryWithReturnValue("SELECT * FROM product_category");
    }
}
