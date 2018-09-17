package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartProductDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.ShoppingCartProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
                "WHERE shopping_cart_id = ? AND product_id = ?;",
            Arrays.asList(shoppingCartId, productId));

        return products;
    }

    @Override
    public int addProductToShoppingCart(int shoppingCartId, int productId) {
        List<ShoppingCartProduct> products = findProduct(shoppingCartId, productId);
        int newAmount;

        if (products.size() == 0) {
            newAmount = controller.executeQueryWithIntReturnValue(
            "INSERT INTO shopping_cart_products (shopping_cart_id, product_id, amount) " +
                    "VALUES (?, ?, 1) " +
                    "RETURNING amount;",
                Arrays.asList(shoppingCartId, productId));
        } else {
            newAmount = controller.executeQueryWithIntReturnValue(
            "UPDATE shopping_cart_products SET amount = (? + 1) " +
                    "WHERE shopping_cart_id = ? AND product_id = ? " +
                    "RETURNING amount;",
                Arrays.asList(products.get(0).getAmount(), shoppingCartId, productId));
        }

        return newAmount;
    }

    @Override
    public int removeProductToShoppingCart(int shoppingCartId, int productId) {
        int productAmount = findProduct(shoppingCartId, productId).get(0).getAmount();
        int newAmount;

        if (productAmount == 1) {
            newAmount = controller. executeQueryWithIntReturnValue(
                    "DELETE FROM shopping_cart_products " +
                            "WHERE shopping_cart_id = ? AND product_id = ? " +
                            "RETURNING 0;",
                    Arrays.asList(shoppingCartId, productId));
        } else {
            newAmount = controller.executeQueryWithIntReturnValue(
                    "UPDATE shopping_cart_products SET amount = (? - 1) " +
                            "WHERE shopping_cart_id = ? AND product_id = ? " +
                            "RETURNING amount;",
                    Arrays.asList(productAmount, shoppingCartId, productId));
        }
        return newAmount;
    }

    @Override
    public int getProductAmountInCart(int shoppingCartId) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int data = 0;

        try {
            preparedStatement = connection.prepareStatement(
            "SELECT SUM(amount) AS total_amount FROM shopping_cart_products " +
                    "WHERE shopping_cart_id = ?;");
            preparedStatement.setInt(1, shoppingCartId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data = resultSet.getInt("total_amount");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return data;
    }

    @Override
    public float getTotalPriceInCart(int shoppingCartId) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        float data = 0;

        try {
            preparedStatement = connection.prepareStatement(
            "SELECT SUM(amount * product.default_price ) AS total_price " +
                    "FROM shopping_cart_products JOIN product ON shopping_cart_products.product_id = product.id " +
                    "WHERE shopping_cart_id = ?;");

            preparedStatement.setInt(1, shoppingCartId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data = resultSet.getFloat("total_price");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return data;
    }

    @Override
    public List<Map<String, Object>> getOrderHistory(int userId) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT shopping_cart.time,SUM(shopping_cart_products.amount) as amount, " +
                            "    SUM(product.default_price) as total_price " +
                            "    FROM shopping_cart " +
                            "    JOIN shopping_cart_products on shopping_cart.id = shopping_cart_id " +
                            "    JOIN product on product_id = product.id " +
                            "    WHERE user_id = ? " +
                            "    GROUP BY shopping_cart.time;");

            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Map<String, Object> data = new HashMap<>();
                data.put("amount", resultSet.getInt("amount"));
                data.put("time", resultSet.getString("time"));
                data.put("totalPrice", resultSet.getString("total_price"));

                resultList.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> getShoppingCartProducts(int shoppingCartId) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(
            "SELECT product.id, product.name, product.description, product.default_price, " +
                    "       product_category.name AS product_category, " +
                    "       supplier.name AS supplier, shopping_cart_products.amount " +
                    "  FROM product " +
                    "       JOIN product_category ON product.product_category_id = product_category.id " +
                    "       JOIN supplier ON product.supplier_id = supplier.id " +
                    "       JOIN shopping_cart_products ON product.id = shopping_cart_products.product_id " +
                    "       JOIN shopping_cart ON shopping_cart_products.shopping_cart_id = shopping_cart.id " +
                    "  WHERE shopping_cart_products.shopping_cart_id = ? " +
                    "  ORDER BY product.id;");

            preparedStatement.setInt(1, shoppingCartId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("id", resultSet.getInt("id"));
                data.put("name", resultSet.getString("name"));
                data.put("description", resultSet.getString("description"));
                data.put("defaultPrice", resultSet.getFloat("default_price"));
                data.put("productCategory", resultSet.getString("product_category"));
                data.put("supplier", resultSet.getString("supplier"));
                data.put("amount", resultSet.getInt("amount"));

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
}
