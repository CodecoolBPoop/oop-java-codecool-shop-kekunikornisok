package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class /*ShoppingCartDaoJDBC implements*/ ShoppingCartDaoJDBC {
    private static final JDBCController controller = JDBCController.getInstance();

    public List<ShoppingCart> executeQueryWithReturnValue(String query) {
        List<ShoppingCart> resultList = new ArrayList<>();

        try (Connection connection = controller.getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                ShoppingCart data = new ShoppingCart(resultSet.getInt("user_id"),
                        resultSet.getDate("time"),
                        resultSet.getString("status"));

                resultList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    //product-ba add? másképp? plusz modell S Cart
    public void addToDatabase(ShoppingCart shoppingCart) {
        controller.executeQuery("INSERT INTO shopping_cart (id, user_id, time, status) VALUES (DEFAULT, " +
                shoppingCart.getUserId() + ", '" + shoppingCart.getTime() + "', '" + shoppingCart.getStatus()+ "';");
    }

    public ShoppingCart findUserCart(int id){
        return executeQueryWithReturnValue("SELECT * FROM shopping_cart WHERE id = '" + id + "';").get(0);
    }

    public ShoppingCart findFUCK(String name) {
        return executeQueryWithReturnValue("SELECT * FROM shopping_cart WHERE id = '" + name + "';").get(0);
    }


    public void removeShoppingCart(int id) { controller.executeQuery("DELETE FROM shopping_cart WHERE id = '" + id + "';"); }

    public List<ShoppingCart> getAllCart() {
        return executeQueryWithReturnValue("SELECT * FROM shopping_cart");
    }

}
