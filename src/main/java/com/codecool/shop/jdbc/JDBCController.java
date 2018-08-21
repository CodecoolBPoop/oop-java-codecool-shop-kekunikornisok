package com.codecool.shop.jdbc;

import java.sql.*;

public class JDBCController {

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/proman";
    private static final String DB_USER = System.getenv("MY_PSQL_USER");
    private static final String DB_PASSWORD = System.getenv("MY_PSQL_PASSWORD");
    private static JDBCController instance = null;


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String executeQueryWithReturnValue(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            if (resultSet.next()){
                return String.valueOf(resultSet);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JDBCController getInstance() {
        if (instance == null) {
            instance = new JDBCController();
        }
        return instance;
    }

}
