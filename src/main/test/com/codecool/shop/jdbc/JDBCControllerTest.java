package com.codecool.shop.jdbc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JDBCControllerTest {

    private static JDBCController controller = null;
    private static Connection connection = null;

    @BeforeAll
    static void setUp() {
        controller = JDBCController.getInstance();
        connection = controller.getConnection();
    }

    @AfterAll
    static void teatDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isExecuteQueryThrowsPSQLException() {
        assertThrows(PSQLException.class, () -> controller.executeQuery("SELECT * FROM asdasda", Collections.emptyList()));
    }

}