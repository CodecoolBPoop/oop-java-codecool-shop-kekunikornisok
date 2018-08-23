package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoJBDC implements UserDao {
    private static final JDBCController controller = JDBCController.getInstance();
    private static UserDaoJBDC instance = null;

    public static UserDaoJBDC getInstance() {
        if (instance == null) {
            instance = new UserDaoJBDC();
        }
        return instance;
    }

    public List<User> executeQueryWithReturnValue(String query) {
        List<User> resultList = new ArrayList<>();

        try (Connection connection = controller.getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                User data = new User(resultSet.getInt("id"),
                        resultSet.getString("email_address"),
                        resultSet.getString("password"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("address"),
                        resultSet.getString("zip_code"),
                        resultSet.getBoolean("is_shipping_same"));

                resultList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public void add(String emailAddress, String password) {
        controller.executeQuery("INSERT INTO users (id, email_address, password, first_name, last_name, country, city, address, zip_code, is_shipping_same) " +
                "VALUES (DEFAULT, '" +  emailAddress + "', '" + password + "', 'None',  'None', 'None', 'None', 'None', 'None', false);");
    }

    @Override
    public void add(String emailAddress, String password, String firstName, String lastName, String country, String city, String address, String zipCode, boolean isShippingSame) {
        controller.executeQuery("INSERT INTO users (id, email_address, password, first_name, last_name, country, city, address, zip_code, is_shipping_same)" +
                "VALUES (DEFAULT, '" +  emailAddress + "', '" + password + "', '" + firstName +
                "', '" + lastName + "', '" + country + "', '" + city + "', '" + address +
                "', '" + zipCode + "', '" + isShippingSame + "';");
    }

    @Override
    public void setTable(int id, String firstName, String lastName, String country, String city, String address, String zipCode) {
        controller.executeQuery("UPDATE users SET first_name = '" + firstName + "', last_name = '" + lastName + "', country = '" + country +
                "', city = '" + city + "', address = '" + address + "', zip_code = '" + zipCode + "' WHERE id = '" + id + "';");
    }

    @Override
    public User find(int id) {
        return executeQueryWithReturnValue("SELECT * FROM users WHERE id = '" + id + "';").get(0);
    }

    @Override
    public User find(String email) {
        return executeQueryWithReturnValue("SELECT * FROM users WHERE email_address LIKE '" + email + "';").get(0);
    }

    @Override
    public void remove(int id) {
        controller.executeQuery("DELETE FROM users WHERE id = '" + id + "';");
    }

    @Override
    public List<User> getAll() {
        return executeQueryWithReturnValue("SELECT * FROM users;");
    }

    @Override
    public List<String> getEmails() {
        return executeQueryWithReturnValue("SELECT * FROM users;").stream()
                                                                  .map(User::getEmailAddress)
                                                                  .collect(Collectors.toList());
    }

    @Override
    public boolean validRegister(String email, String password, String passwordConfirm) {
        return !getEmails().contains(email) && password.equals(passwordConfirm);
    }

    @Override
    public boolean validLogin(String email, String password) {
        User user;
        try {
            user = this.find(email);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return user.getEmailAddress().equals(email) && user.getPassword().equals(password);
    }
}
