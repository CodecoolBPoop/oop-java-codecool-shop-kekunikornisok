package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.jdbc.JDBCController;
import org.junit.jupiter.api.*;
import org.postgresql.util.PSQLException;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartProductDaoTest {

    private static JDBCController controller = JDBCController.getInstance();
    private static ProductCategoryDao productCategory = null;
    private static SupplierDao supplier = null;
    private static ProductDao product = null;
    private static UserDao user = null;
    private static ShoppingCartDao shoppingCart = null;
    private static ShoppingCartProductDao shoppingCartProduct = null;

    @BeforeAll
    static void setUp() {
        productCategory = ProductCategoryDaoJDBC.getInstance();
        supplier = SupplierDaoJDBC.getInstance();
        product = ProductDaoJDBC.getInstance();
        user = UserDaoJDBC.getInstance();
        shoppingCart = ShoppingCartDaoJDBC.getInstance();
        shoppingCartProduct = ShoppingCartProductDaoJDBC.getInstance();

        controller.executeQuery(
        "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.product_category', 'id')), 1, FALSE);" +
                "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.supplier', 'id')), 1, FALSE);" +
                "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.product', 'id')), 1, FALSE);" +
                "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.users', 'id')), 1, FALSE);" +
                "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.shopping_cart', 'id')), 1, FALSE);",
            Collections.emptyList());

        productCategory.add("Lölö", "Gázszerelő", "Felcsút");
        productCategory.add("Áder Jancsibá", "Mosztázs", "Fidesz");

        supplier.add("Deutsch Tomi", "Jobban teljesít");
        supplier.add("HeliAnti", "Helikopáter");

        product.add("Vitya", 300, "USD", "az Isten",
                productCategory.find(1),
                supplier.find(1));

        product.add("Sziszi", 600, "USD", "az Isten",
                productCategory.find(2),
                supplier.find(2));

        user.add("#Szili@gmail.com", "kétharmad");

        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
    }

    @AfterAll
    static void tearDown() {
        controller.executeQuery(
        "TRUNCATE TABLE codecool_shop_test.public.product_category CASCADE;" +
                "TRUNCATE TABLE codecool_shop_test.public.supplier CASCADE;" +
                "TRUNCATE TABLE codecool_shop_test.public.product CASCADE;" +
                "TRUNCATE TABLE codecool_shop_test.public.users CASCADE;" +
                "TRUNCATE TABLE codecool_shop_test.public.shopping_cart CASCADE;" +
                "TRUNCATE TABLE codecool_shop_test.public.shopping_cart_products CASCADE;",
            Collections.emptyList());
    }

    @Test
    void testAddNewProductToDatabaseIsLengthCorrect() {
        shoppingCartProduct.addProductToShoppingCart(1, 1);
        int tableSize = shoppingCartProduct.getShoppingCartProducts(1).size();
        shoppingCartProduct.addProductToShoppingCart(1, 2);
        assertEquals(tableSize + 1, shoppingCartProduct.getShoppingCartProducts(1).size());
        shoppingCartProduct.removeProductToShoppingCart(1,1);
        shoppingCartProduct.removeProductToShoppingCart(1,2);
    }

    @Test
    void testAddNewProductToDatabaseIsAmountCorrect() {
        assertEquals(1, shoppingCartProduct.addProductToShoppingCart(1, 1));
        shoppingCartProduct.removeProductToShoppingCart(1,1);
    }

    @Test
    void testAddNewProductToDatabaseIncorrectShoppingCartId() {
        assertEquals(1, shoppingCartProduct.addProductToShoppingCart(2, 1));
        shoppingCartProduct.removeProductToShoppingCart(2,1);
    }

    @Test
    void testAddNewProductToDatabaseIncorrectProductId() {
        assertEquals(1, shoppingCartProduct.addProductToShoppingCart(1, 4));
        shoppingCartProduct.removeProductToShoppingCart(2,1);
    }

    @Test
    void testAddAlreadyExistingProductToDatabaseIsAmountCorrect() {
        shoppingCartProduct.addProductToShoppingCart(1, 1);
        int amount = (Integer) shoppingCartProduct.getShoppingCartProducts(1).get(0).get("amount");
        shoppingCartProduct.addProductToShoppingCart(1, 1);
        shoppingCartProduct.addProductToShoppingCart(1, 1);
        assertEquals(amount + 2, shoppingCartProduct.getShoppingCartProducts(1).get(0).get("amount"));
        shoppingCartProduct.removeProductToShoppingCart(1,1);
        shoppingCartProduct.removeProductToShoppingCart(1,1);
        shoppingCartProduct.removeProductToShoppingCart(1,1);
    }

    @Test
    void testRemoveProductFromDatabaseIsLengthCorrect() {
        shoppingCartProduct.addProductToShoppingCart(1, 1);
        shoppingCartProduct.addProductToShoppingCart(1, 2);
        int tableSize = shoppingCartProduct.getShoppingCartProducts(1).size();
        shoppingCartProduct.removeProductToShoppingCart(1, 1);
        assertEquals(tableSize - 1, shoppingCartProduct.getShoppingCartProducts(1).size());
        shoppingCartProduct.removeProductToShoppingCart(1,2);
    }

    @Test
    void testRemoveProductFromDatabaseWhenAmountWillNotBeZero() {
        shoppingCartProduct.addProductToShoppingCart(1, 1);
        shoppingCartProduct.addProductToShoppingCart(1, 1);
        shoppingCartProduct.addProductToShoppingCart(1, 1);
        int amount = (Integer) shoppingCartProduct.getShoppingCartProducts(1).get(0).get("amount");
        shoppingCartProduct.removeProductToShoppingCart(1, 1);
        shoppingCartProduct.removeProductToShoppingCart(1, 1);
        assertEquals(amount - 2, shoppingCartProduct.getShoppingCartProducts(1).get(0).get("amount"));
        shoppingCartProduct.removeProductToShoppingCart(1,1);
    }

    @Test
    void testRemoveProductFromDatabaseWhenAmountWillBeZero() {
        shoppingCartProduct.addProductToShoppingCart(1, 1);
        assertEquals(0, shoppingCartProduct.removeProductToShoppingCart(1, 1));
    }

    @Test
    void testRemoveProductFromDatabaseIncorrectShoppingCartId() {
        assertEquals(0, shoppingCartProduct.removeProductToShoppingCart(2, 1));
    }

    @Test
    void testRemoveProductFromDatabaseIncorrectProductId() {
        assertEquals(0, shoppingCartProduct.removeProductToShoppingCart(1, 4));
    }

    @Test
    void testTotalAmountInCartIncorrectShoppingCartId() {
        assertEquals(0, shoppingCartProduct.getProductAmountInCart(2));
    }

    @Test
    void testTotalAmountInCartCorrectShoppingCartId() {
        shoppingCartProduct.addProductToShoppingCart(1,1);
        shoppingCartProduct.addProductToShoppingCart(1,1);
        assertEquals(2, shoppingCartProduct.getProductAmountInCart(1));
        shoppingCartProduct.removeProductToShoppingCart(1,1);
        shoppingCartProduct.removeProductToShoppingCart(1,1);
    }

    @Test
    void testTotalPriceInCartIncorrectShoppingCartId() {
        assertEquals(0, shoppingCartProduct.getTotalPriceInCart(2));
    }

    @Test
    void testTotalPriceInCartCorrectShoppingCartId() {
        shoppingCartProduct.addProductToShoppingCart(1,1);
        shoppingCartProduct.addProductToShoppingCart(1,1);
        assertEquals(600, shoppingCartProduct.getTotalPriceInCart(1));
        shoppingCartProduct.removeProductToShoppingCart(1,1);
        shoppingCartProduct.removeProductToShoppingCart(1,1);
    }

    @Test
    void testGetOrderHistoryCorrectLengthValidInput() {
        shoppingCartProduct.addProductToShoppingCart(1,1);
        assertEquals(1, shoppingCartProduct.getOrderHistory(1).size());
        shoppingCartProduct.removeProductToShoppingCart(1,1);
    }

    @Test
    void testGetOrderHistoryCorrectLengthInvalidInput() {
        shoppingCartProduct.addProductToShoppingCart(1,1);
        assertEquals(0, shoppingCartProduct.getOrderHistory(2).size());
        shoppingCartProduct.removeProductToShoppingCart(1,1);
    }

    @Test
    void testGetShoppingCartProductsCorrectLengthValidInput() {
        shoppingCartProduct.addProductToShoppingCart(1,1);
        assertEquals(1, shoppingCartProduct.getOrderHistory(1).size());
        shoppingCartProduct.removeProductToShoppingCart(1,1);
    }

    @Test
    void testGetShoppingCartProductsCorrectLengthInvalidInput() {
        shoppingCartProduct.addProductToShoppingCart(1,1);
        assertEquals(0, shoppingCartProduct.getOrderHistory(2).size());
        shoppingCartProduct.removeProductToShoppingCart(1,1);
    }

}