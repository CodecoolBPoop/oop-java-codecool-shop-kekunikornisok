package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShippingAddressDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.ShippingAddress;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    public List<ShippingAddress> executeQueryWithReturnValue(String query) {
        List<ShippingAddress> resultList = new ArrayList<>();

        try (Connection connection = controller.getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
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
        }

        return resultList;
    }

    @Override
    public void add(int id, String country, String city, String address, String zipCode) {
        controller.executeQuery("INSERT INTO shipping_address (user_id, country, city, address, zip_code) " +
                "VALUES ('" + id + "', '" + country + "', '" + city +
                "', '" + address + "', '" + zipCode + "');");
    }

    @Override
    public void setTable(int id, String country, String city, String address, String zipCode) {
        controller.executeQuery("UPDATE shipping_address SET country = '" + country + "', city = '" + city + "', address = '" + address +
                "', zip_code = '" + zipCode + "' WHERE user_id = '" + id + "';");
    }

    public List<Integer> getUserId() {
        return executeQueryWithReturnValue("SELECT * FROM shipping_address;").stream()
                                                                             .map(ShippingAddress::getUserId)
                                                                             .collect(Collectors.toList());
    }
}
