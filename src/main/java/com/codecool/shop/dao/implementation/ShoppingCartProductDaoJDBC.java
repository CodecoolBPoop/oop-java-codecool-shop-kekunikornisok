package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartProductDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.ShoppingCartProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingCartProductDaoJDBC implements ShoppingCartProductDao {
    private static final JDBCController controller = JDBCController.getInstance();
    private static ShoppingCartProductDaoJDBC instance = null;

    public static ShoppingCartProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ShoppingCartProductDaoJDBC();
        }
        return instance;
    }

    private List<ShoppingCartProduct> executeQueryWithReturnValue(String query, List<Object> parameters) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ShoppingCartProduct> resultList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ShoppingCartProduct data = new ShoppingCartProduct(resultSet.getInt("shopping_cart_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("amount"));
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

    private List<ShoppingCartProduct> findProduct(int shoppingCartId, int productId) {
        List<ShoppingCartProduct> products = executeQueryWithReturnValue(
        "SELECT * FROM shopping_cart_products " +
                "WHERE shopping_card_id = ? AND product_id = ?;",
            Arrays.asList(shoppingCartId, productId));

        return products;
    }

    @Override
    public void addProductToShoppingCart(int shoppingCartId, int productId) {
        List<ShoppingCartProduct> products = findProduct(shoppingCartId, productId);

        if (products.size() == 0) {
            controller.executeQuery(
            "INSERT INTO shopping_cart_products (shopping_card_id, product_id, amount) " +
                    "VALUES (?, ?, 1);",
                Arrays.asList(shoppingCartId, productId));
        } else {
            controller.executeQuery(
            "UPDATE shopping_cart_products SET amount = (? + 1) " +
                    "WHERE shopping_card_id = ? AND product_id = ?;",
                Arrays.asList(products.get(0).getAmount(), shoppingCartId, productId));
        }
    }

    @Override
    public void removeProductToShoppingCart(int shoppingCartId, int productId) {
        int productAmount = findProduct(shoppingCartId, productId).get(0).getAmount();

        if (productAmount == 1) {
            controller.executeQuery(
                    "DELETE FROM shopping_cart_products " +
                            "WHERE shopping_card_id = ? AND product_id = ?;",
                    Arrays.asList(shoppingCartId, productId));
        } else {
            controller.executeQuery(
                    "UPDATE shopping_cart_products SET amount = (? - 1) " +
                            "WHERE shopping_card_id = ? AND product_id = ?;",
                    Arrays.asList(productAmount, shoppingCartId, productId));
        }
    }
}
