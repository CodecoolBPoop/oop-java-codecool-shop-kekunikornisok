package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.ShoppingCartStatus;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ShoppingCartDaoJDBC implements ShoppingCartDao {
    private static final JDBCController controller = JDBCController.getInstance();
    private static ProductDaoJDBC instance = null;

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    private List<ShoppingCart> executeQueryWithReturnValue(String query, List<Object> parameters) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ShoppingCart> resultList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ShoppingCart data = new ShoppingCart(resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getDate("time"),
                        ShoppingCartStatus.valueOf(resultSet.getString("status")));
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
    public void add(int userId, Date time, ShoppingCartStatus status) {
        controller.executeQuery(
        "INSERT INTO shopping_cart (id, user_id, time, status) " +
                "VALUES (DEFAULT, ?, ?, ?);",
            Arrays.asList(userId, time, status.toString()));
    }

    @Override
    public ShoppingCart find(int id) {
        List<ShoppingCart> shoppingCarts = executeQueryWithReturnValue(
        "SELECT * FROM shopping_cart WHERE id = ?;",
            Collections.singletonList(id));

        return (shoppingCarts.size() != 0) ? shoppingCarts.get(0) : null;
    }

    @Override
    public ShoppingCart find(String name) {
        List<ShoppingCart> shoppingCarts = executeQueryWithReturnValue(
        "SELECT * FROM shopping_cart WHERE nane LIKE ?;",
            Collections.singletonList(name));

        return (shoppingCarts.size() != 0) ? shoppingCarts.get(0) : null;
    }

    @Override
    public ShoppingCart findActiveCart() {
        List<ShoppingCart> shoppingCarts = executeQueryWithReturnValue(
        "SELECT * FROM shopping_cart WHERE status LIKE ?;",
            Collections.singletonList("IN_CART"));

        return (shoppingCarts.size() != 0) ? shoppingCarts.get(0) : null;
    }

    @Override
    public void remove(int id) {
        controller.executeQuery(
        "DELETE FROM shopping_cart WHERE id = ?;",
            Collections.singletonList(id));
    }

    @Override
    public List<ShoppingCart> getAll() {
        return executeQueryWithReturnValue(
        "SELECT * FROM shopping_cart;",
            Collections.emptyList());
    }

}
