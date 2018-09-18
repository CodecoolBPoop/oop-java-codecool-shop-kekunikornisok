package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.jdbc.JDBCController;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    private static JDBCController controller = JDBCController.getInstance();
    private static ProductCategoryDao productCategory = null;

    @BeforeAll
    static void setUp() {
        productCategory = ProductCategoryDaoJDBC.getInstance();
    }

    @BeforeEach
    void setUpEach() {
        controller.executeQuery(
        "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.product_category', 'id')), 1, FALSE);",
            Collections.emptyList());
    }

    @AfterEach
    void tearDownEach() {
        controller.executeQuery(
        "TRUNCATE TABLE codecool_shop_test.public.product_category CASCADE;",
            Collections.emptyList());
    }

    @Test
    void testAddDataToDatabase() {
        int tableSize = productCategory.getAll().size();
        productCategory.add("Vitya", "az", "Isten");
        assertEquals(tableSize + 1, productCategory.getAll().size());
    }

    @Test
    void testFindInDatabaseWhenParameterIsNull() {
        assertNull(productCategory.find(null));
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidInteger() {
        productCategory.add("Vitya", "az", "Isten");
        assertNotNull(productCategory.find(1));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidInteger() {
        productCategory.add("Vitya", "az", "Isten");
        assertNull(productCategory.find(10000));
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidString() {
        productCategory.add("Vitya", "az", "Isten");
        assertNotNull(productCategory.find("Vitya"));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidString() {
        productCategory.add("Vitya", "az", "Isten");
        assertNull(productCategory.find("Vitttyaaa"));
    }

    @Test
    void testFindInDatabaseWhenParameterIsEmptyString() {
        productCategory.add("Vitya", "az", "Isten");
        assertNull(productCategory.find(""));
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsPresent() {
        productCategory.add("Vitya", "az", "Isten");
        productCategory.remove(1);
        assertTrue(productCategory.getAll().isEmpty());
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsNotPresent() {
        productCategory.add("Vitya", "az", "Isten");
        productCategory.remove(10000);
        assertFalse(productCategory.getAll().isEmpty());
    }

    @Test
    void testGetAllWhenTableIsEmpty() {
        assertTrue(productCategory.getAll().isEmpty());
    }

    @Test
    void testGetAllWhenTableHasOneRow() {
        productCategory.add("Vitya", "az", "Isten");
        assertEquals(1, productCategory.getAll().size());
    }

    @Test
    void testGetAllWhenTableHasMoreRows() {
        productCategory.add("Vitya", "az", "Isten");
        productCategory.add("Lölö", "az Isten", "bal keze");
        assertEquals(2, productCategory.getAll().size());
    }

}