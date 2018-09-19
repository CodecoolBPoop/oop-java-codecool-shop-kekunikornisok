package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ShippingAddressDaoJDBC;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import com.codecool.shop.jdbc.JDBCController;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ShippingAddressDaoTest {

    private static JDBCController controller = JDBCController.getInstance();
    private static UserDao user = null;
    private static ShippingAddressDao shippingAddress = null;

    @BeforeAll
    static void setUp() {
        user = UserDaoJDBC.getInstance();
        shippingAddress = ShippingAddressDaoJDBC.getInstance();

        controller.executeQuery(
        "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.users', 'id')), 1, FALSE);",
            Collections.emptyList());

        user.add("#Szili@gmail.com", "kétharmad");
    }

    @AfterEach
    void tearDownEach() {
        controller.executeQuery(
        "TRUNCATE TABLE codecool_shop_test.public.shipping_address CASCADE;",
            Collections.emptyList());
    }

    @AfterAll
    static void tearDown() {
        controller.executeQuery(
        "TRUNCATE TABLE codecool_shop_test.public.users CASCADE;",
            Collections.emptyList());
    }

    @Test
    void testAddDataToDatabaseAllParameter() {
        int tableSize = shippingAddress.getUserId().size();
        shippingAddress.add(1, "Vitya", "Isten", "Lölö", "Gáz");
        assertEquals(tableSize + 1, shippingAddress.getUserId().size());
    }

    @Test
    void testSetTableEmptyStringInput() {
        shippingAddress.add(1, "Vitya", "Isten", "Lölö", "Gáz");
        shippingAddress.setTable(1,"", "Isten", "Lölö", "Gáz");
        assertEquals("", shippingAddress.find(1).get(0).getCountry());
    }

    @Test
    void testSetTableEmptyValidInput() {
        shippingAddress.add(1, "Vitya", "Isten", "Lölö", "Gáz");
        shippingAddress.setTable(1,"Lölö", "Isten", "LöLö", "Gáz");
        assertEquals("Lölö", shippingAddress.find(1).get(0).getCountry());
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidInteger() {
        shippingAddress.add(1, "Vitya", "Isten", "Lölö", "Gáz");
        assertNotNull(shippingAddress.find(1));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidInteger() {
        shippingAddress.add(1, "Vitya", "Isten", "Lölö", "Gáz");
        assertNotNull(shippingAddress.find(Integer.MAX_VALUE));
    }

    @Test
    void testGetAllWhenTableIsEmpty() {
        assertTrue(shippingAddress.getUserId().isEmpty());
    }

    @Test
    void testGetAllValidInput() {
        shippingAddress.add(1, "Vitya", "Isten", "Lölö", "Gáz");
        assertEquals(1, shippingAddress.getUserId().size());
    }

    @Test
    void testGetAllInvalidInput() {
        shippingAddress.add(1, "Vitya", "Isten", "Lölö", "Gáz");
        assertEquals(1, shippingAddress.getUserId().size());
    }

}