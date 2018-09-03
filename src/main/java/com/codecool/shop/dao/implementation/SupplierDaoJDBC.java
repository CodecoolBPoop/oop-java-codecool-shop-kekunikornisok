package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.*;

public class SupplierDaoJDBC implements SupplierDao {
    private static final JDBCController controller = JDBCController.getInstance();
    private static SupplierDaoJDBC instance = null;

    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    private List<Supplier> executeQueryWithReturnValue(String query, List<Object> parameters) {
        Connection connection = controller.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Supplier> resultList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Supplier data = new Supplier(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
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
    public void add(String name, String description) {
        controller.executeQuery(
        "INSERT INTO supplier(id, name, description) " +
                "VALUES (DEFAULT, ?, ?);",
            Arrays.asList(name, description));
    }

    @Override
    public Supplier find(int id) {
        List<Supplier> suppliers = executeQueryWithReturnValue(
        "SELECT * FROM supplier WHERE id = ?;",
            Collections.singletonList(id));

        return (suppliers.size() != 0) ? suppliers.get(0) : null;
    }

    @Override
    public Supplier find(String name) {
        List<Supplier> suppliers = executeQueryWithReturnValue(
        "SELECT * FROM supplier WHERE name LIKE ?;",
            Collections.singletonList(name));
            
        return (suppliers.size() != 0) ? suppliers.get(0) : null;
    }

    @Override
    public void remove(int id) {
        controller.executeQuery(
        "DELETE FROM supplier WHERE id = ?;",
            Collections.singletonList(id));
    }

    @Override
    public List<Supplier> getAll() {
        return executeQueryWithReturnValue(
        "SELECT * FROM supplier",
            Collections.emptyList());
    }
}
