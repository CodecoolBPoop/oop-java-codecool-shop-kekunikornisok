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
    private static ShoppingCartDaoJDBC instance = null;

    public static ShoppingCartDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ShoppingCartDaoJDBC();
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
    public void add(int userId, Date time) {
        controller.executeQuery(
        "INSERT INTO shopping_cart (id, user_id, time, status) " +
                "VALUES (DEFAULT, ?, ?, 'IN_CART');",
            Arrays.asList(userId, time));
    }

    @Override
    public ShoppingCart find(int id) {
        List<ShoppingCart> shoppingCarts = executeQueryWithReturnValue(
        "SELECT * FROM shopping_cart WHERE id = ?;",
            Collections.singletonList(id));

        return (shoppingCarts.size() != 0) ? shoppingCarts.get(0) : null;
    }

    @Override
    public ShoppingCart findActiveCartForUser(int userId) {
        List<ShoppingCart> shoppingCarts = executeQueryWithReturnValue(
        "SELECT * FROM shopping_cart WHERE user_id = ? AND status LIKE 'IN_CART';",
            Collections.singletonList(userId));

        return (shoppingCarts.size() != 0) ? shoppingCarts.get(0) : null;
    }

    @Override
    public void changeCartStatus(int userId, ShoppingCartStatus statusFrom, ShoppingCartStatus statusTo) {
        controller.executeQuery(
        "UPDATE shopping_cart SET status = ? " +
                "WHERE user_id = ? AND status = ?;",
            Arrays.asList(statusTo.toString(), userId, statusFrom.toString()));
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
