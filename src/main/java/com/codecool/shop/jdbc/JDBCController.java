package com.codecool.shop.jdbc;

import java.sql.*;
import java.util.List;

public class JDBCController {

    private static final String DATABASE = "jdbc:postgresql://" + System.getenv("MY_PSQL_HOST") + ":5432/" + System.getenv("MY_PSQL_DBNAME");
    private static final String DB_USER = System.getenv("MY_PSQL_USER");
    private static final String DB_PASSWORD = System.getenv("MY_PSQL_PASSWORD");
    private static JDBCController instance = null;


    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    DATABASE,
                    DB_USER,
                    DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static JDBCController getInstance() {
        if (instance == null) {
            instance = new JDBCController();
        }
        return instance;
    }

    public void executeQuery(String query, List<Object> parameters) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (connection != null) connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public int executeQueryWithIntReturnValue(String query, List<Object> parameters) {
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return result;
    }

}
