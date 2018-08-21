package com.codecool.shop.jdbc;

public class Queries {

    private static final JDBCController controller = JDBCController.getInstance();
    private static Queries instance = null;

    public static String getBoards() {
        return controller.executeQueryWithReturnValue("SELECT * FROM boards;");
    }

    public static Queries getInstance() {
        if (instance == null) {
            instance = new Queries();
        }
        return instance;
    }

}
