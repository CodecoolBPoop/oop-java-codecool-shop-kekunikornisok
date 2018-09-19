package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ShoppingCartDaoJDBC;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import com.codecool.shop.jdbc.JDBCController;
import com.codecool.shop.model.ShoppingCartStatus;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartDaoTest {
    private static JDBCController controller = JDBCController.getInstance();
    private static UserDao user = null;
    private static ShoppingCartDao shoppingCart = null;

    @BeforeAll
    static void setUp() {
        user = UserDaoJDBC.getInstance();
        shoppingCart = ShoppingCartDaoJDBC.getInstance();
    }

    @BeforeEach
    void setUpEach() {

        controller.executeQuery(
                "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.shopping_cart', 'id')), 1, FALSE);" +
                        "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.users', 'id')), 1, FALSE);",
                Collections.emptyList());

    }

    @AfterEach
    void tearDownEach() {
        controller.executeQuery(
                "TRUNCATE TABLE codecool_shop_test.public.shopping_cart CASCADE;" +
                        "TRUNCATE TABLE codecool_shop_test.public.users CASCADE;",
                Collections.emptyList());
    }

    @AfterAll
    static void tearDownAll() {
        controller.executeQuery(
                "TRUNCATE TABLE codecool_shop_test.public.shopping_cart CASCADE;" +
                        "TRUNCATE TABLE codecool_shop_test.public.users CASCADE;",
                Collections.emptyList());
    }

    @Test
    void testAddDataToDatabase() {
        user.add("Vityo@gmail.com", "SokAPénzem", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        int tableSize = shoppingCart.getAll().size();
        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        assertEquals(tableSize + 1, shoppingCart.getAll().size());
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidInteger() {
        user.add("Vityo@gmail.com", "SokAPénzem", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );

        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        assertNotNull(shoppingCart.find(1));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidInteger() {
        user.add("Vityo@gmail.com", "SokAPénzem", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        assertNull(shoppingCart.find(Integer.MAX_VALUE));
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsPresent() {
        user.add("Vityo@gmail.com", "SokAPénzem", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        shoppingCart.remove(1);
        assertTrue(shoppingCart.getAll().isEmpty());
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsNotPresent() {
        user.add("Vityo@gmail.com", "SokAPénzem", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        shoppingCart.remove(Integer.MAX_VALUE);
        assertFalse(shoppingCart.getAll().isEmpty());
    }

    @Test
    void testGetAllWhenTableIsEmpty() {
        assertTrue(shoppingCart.getAll().isEmpty());
    }

    @Test
    void testGetAllWhenTableHasOneRow() {
        user.add("Vityo@gmail.com", "SokAPénzem", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        assertEquals(1, shoppingCart.getAll().size());
    }

    @Test
    void testGetAllWhenTableHasMoreRows() {
        user.add("Vityo@gmail.com", "Cuncika", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        user.add("Orban@citromail.com", "Cuncika", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        shoppingCart.add(2, new java.sql.Date(new Date().getTime()));

        assertEquals(2, shoppingCart.getAll().size());
    }

    @Test
    void testFindActiveCartForUserGetValidUserId() {
        user.add("Vityo@gmail.com", "SokAPénzem", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        assertNotNull(shoppingCart.findActiveCartForUser(1));
    }

    @Test
    void testFindActiveCartForUserGetInValidUserId() {
        user.add("Vityo@gmail.com", "SokAPénzem", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        assertNull(shoppingCart.findActiveCartForUser(Integer.MAX_VALUE));
    }

    @Test
    void testIsChangeCardStatus() {
        user.add("Vityo@gmail.com", "SokAPénzem", "Orbán", "Viktor", "Mo", "Bp", "Buda", "4027", false );
        shoppingCart.add(1, new java.sql.Date(new Date().getTime()));
        shoppingCart.changeCartStatus(1, ShoppingCartStatus.IN_CART, ShoppingCartStatus.CHECKED);
        assertEquals(ShoppingCartStatus.CHECKED, shoppingCart.find(1).getStatus());
    }

}