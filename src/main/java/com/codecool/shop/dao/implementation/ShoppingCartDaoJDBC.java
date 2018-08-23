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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ShoppingCartDaoJDBC implements ShoppingCartDao {
    private static final JDBCController controller = JDBCController.getInstance();
    private static ShoppingCartDaoJDBC instance = null;
    private static List<ShoppingCart> listOfCarttoUser = new ArrayList<>();


    public static ShoppingCartDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ShoppingCartDaoJDBC();
        }
        return instance;
    }

    public List<ShoppingCart> executeQueryWithReturnValue(String query) {
        List<ShoppingCart> resultList = new ArrayList<>();

        try (Connection connection = controller.getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                ShoppingCart data = new ShoppingCart(resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getDate("time"),
                        resultSet.getString("status"));

                resultList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public void add(Product product, ShoppingCart shoppingCart){
        shoppingCart.addProductToCartList(product);
    }
    @Override
    public Product find(int id, ShoppingCart shoppingCart){
        return shoppingCart.findProductFromCartListById(id);
    }
    @Override
    public Product find(String name, ShoppingCart shoppingCart){
        return shoppingCart.findProductFromCartListByName(name);
    }


    //product-ba add? másképp? plusz modell S Cart
    public void addCartToDatabase(ShoppingCart shoppingCart) {
        controller.executeQuery("INSERT INTO shopping_cart (id, user_id, time, status) VALUES (DEFAULT, " +
                shoppingCart.getUserId() + ", '" + shoppingCart.getTime() + "', '" + shoppingCart.getStatus()+ "';");
    }

    public ShoppingCart findCartId(int id){
        return executeQueryWithReturnValue("SELECT * FROM shopping_cart WHERE id = '" + id + "';").get(0);
    }

    public List<ShoppingCart> findUserCartUserId(int userId) {
        return (executeQueryWithReturnValue("SELECT * FROM shopping_cart WHERE user_id = '" + userId + "';"));
    }
    @Override
    public void remove(int id) { controller.executeQuery("DELETE FROM shopping_cart WHERE id = '" + id + "';"); }

    public void insertNewCartIntoDatabase(){ controller.executeQuery("INSERT INTO shopping_cart (id, user_id, time, status) VALUES (DEFAULT, , '2018-9-20 14:51:00', 'checked');");}

    public List<ShoppingCart> getAllCart() {
        return executeQueryWithReturnValue("SELECT * FROM shopping_cart");
    }

    @Override
    public LinkedHashSet<Product> getAll(ShoppingCart shoppingCart){ return shoppingCart.getAllFromCurrentlyCart(); }

    public void getShoppingCartsFromUsers(int userId){
        listOfCarttoUser = findUserCartUserId(userId);
    }
}