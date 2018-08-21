package com.codecool.shop.jdbc;

import java.sql.*;

public class JDBCController {

    private static final String DATABASE = "jdbc:postgresql://" + System.getenv("MY_PSQL_HOST") + ":5432/" + System.getenv("MY_PSQL_DBNAME");
    private static final String DB_USER = System.getenv("MY_PSQL_USER");
    private static final String DB_PASSWORD = System.getenv("MY_PSQL_PASSWORD");
    private static JDBCController instance = null;


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public static JDBCController getInstance() {
        if (instance == null) {
            instance = new JDBCController();
        }
        return instance;
    }

    public void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
