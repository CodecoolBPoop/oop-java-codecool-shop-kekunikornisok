package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.ShoppingCartProducts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartProductsDaoJDBC {
    private static final JDBCController controller = JDBCController.getInstance();
    private static ShoppingCartProductsDaoJDBC instance = null;
    private ProductDao productDataStore = ProductDaoJDBC.getInstance();

    private static List<Product> listOfCartProductstoUser = new ArrayList<>();

    ShoppingCartDaoJDBC shoppingCartJDBC = ShoppingCartDaoJDBC.getInstance();


    public static ShoppingCartProductsDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ShoppingCartProductsDaoJDBC();
        }
        return instance;
    }

    public List<ShoppingCartProducts> executeQueryWithReturnValue(String query) {
        List<ShoppingCartProducts> resultList = new ArrayList<>();

        try (Connection connection = controller.getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                ShoppingCartProducts data = new ShoppingCartProducts(resultSet.getInt("shopping_card_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("amount"));

                resultList.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
        }
     public List<ShoppingCartProducts> getCartForEachProducts(int shoppingCartId){
         return (executeQueryWithReturnValue("SELECT * FROM shopping_cart_products WHERE shopping_card_id = '" + shoppingCartId + "';"));
     }

     public Product getProducts(int productId){
         return (productDataStore.find("SELECT * FROM product WHERE product_id = '" + productId + "';"));
     }

     public void putProductsToCart(ShoppingCartProducts shoppingCartProducts){
        for(int i = 0; i < shoppingCartProducts.getAmount(); i++)
            listOfCartProductstoUser.add(getProducts(shoppingCartProducts.getProductId()));
     }
}