package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShippingAddressDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.ShippingAddress;
import com.codecool.shop.model.ShoppingCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShippingAddressDaoJDBC implements ShippingAddressDao {
    private static final JDBCController controller = JDBCController.getInstance();
    private static ShippingAddressDaoJDBC instance = null;

    public static ShippingAddressDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ShippingAddressDaoJDBC();
        }
        return instance;
    }

    private List<ShippingAddress> executeQueryWithReturnValue(String query, List<Object> parameters) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ShippingAddress> resultList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                ShippingAddress data = new ShippingAddress(resultSet.getInt("user_id"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("address"),
                        resultSet.getString("zip_code"));

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
    public void add(int userId, String country, String city, String address, String zipCode) {
        controller.executeQuery(
        "INSERT INTO shipping_address (user_id, country, city, address, zip_code) " +
                "VALUES (?, ?, ?, ?, ?);",
            Arrays.asList(userId, country, city, address, zipCode));
    }

    @Override
    public List<ShippingAddress> find(int userId) {
        List<ShippingAddress> shippingAddresses = executeQueryWithReturnValue(
        "SELECT * FROM shipping_address " +
                "WHERE user_id = ?",
            Collections.singletonList(userId));

        if (shippingAddresses.size() == 0) {
            shippingAddresses.add(new ShippingAddress(userId, "", "", "", ""));
        }

        return shippingAddresses;
    }

    @Override
    public void setTable(int userId, String country, String city, String address, String zipCode) {
        controller.executeQuery(
        "UPDATE shipping_address SET country = ?, city = ?, address = ?, zip_code = ? " +
                "WHERE user_id = ?;",
            Arrays.asList(country, city, address, zipCode, userId));
    }

    public List<Integer> getUserId() {
        return executeQueryWithReturnValue(
        "SELECT * FROM shipping_address;",
            Collections.emptyList())
                .stream()
                .map(ShippingAddress::getUserId)
                .collect(Collectors.toList());
    }
}
