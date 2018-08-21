package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private static final JDBCController controller = JDBCController.getInstance();

    public List<Product> executeQueryWithReturnValue(String query) {
        List<Product> resultList = new ArrayList<>();

        try (Connection connection = controller.getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                Product actTodo = new Product(resultSet.getString("name"),
                        resultSet.getFloat("defaultPrice"),
                        resultSet.getString("currencyString"),
                        resultSet.getString("description"),
                        (ProductCategory) resultSet.getObject("supplier_id"),
                        (Supplier) resultSet.getObject("product_category_id"));

                resultList.add(actTodo);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public Product find(String name) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
