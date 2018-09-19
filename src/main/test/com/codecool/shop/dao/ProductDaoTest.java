package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.SupplierDaoJDBC;
import com.codecool.shop.jdbc.JDBCController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {
    private static JDBCController controller = JDBCController.getInstance();
    private static ProductDao product = null;
    private static ProductCategoryDao productCategory = null;
    private static SupplierDao supplier = null;

    @BeforeAll
    static void setUp() {
        product = ProductDaoJDBC.getInstance();
        productCategory = ProductCategoryDaoJDBC.getInstance();
        supplier = SupplierDaoJDBC.getInstance();
    }

    @BeforeEach
    void setUpEach() {
        productCategory.add("Vitya", "az Isten", "Fidesz");
        supplier.add("Lölő", "Vitya jobbkeze");

        controller.executeQuery(
                "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.product', 'id')), 1, FALSE);" +
                      "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.product_category', 'id')), 1, FALSE);" +
                      "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.supplier', 'id')), 1, FALSE);",
                Collections.emptyList());

    }

    @AfterEach
    void tearDownEach() {
        controller.executeQuery(
                "TRUNCATE TABLE codecool_shop_test.public.product CASCADE;" +
                      "TRUNCATE TABLE codecool_shop_test.public.product_category CASCADE;" +
                      "TRUNCATE TABLE codecool_shop_test.public.supplier CASCADE;",
                Collections.emptyList());
    }

    @Test
    void testAddDataToDatabase() {
        int tableSize = product.getAll().size();
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertEquals(tableSize + 1, product.getAll().size());
    }

    @Test
    void testFindInDatabaseWhenParameterIsNull() {
        assertNull(product.find(null));
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidInteger() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertNotNull(product.find(1));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidInteger() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertNull(product.find(Integer.MAX_VALUE));
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidString() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertNotNull(product.find("Vitya"));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidString() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertNull(product.find("Vitttyaaa"));
    }

    @Test
    void testFindInDatabaseWhenParameterIsEmptyString() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertNull(product.find(""));
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsPresent() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        product.remove(1);
        assertTrue(product.getAll().isEmpty());
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsNotPresent() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        product.remove(Integer.MAX_VALUE);
        assertFalse(product.getAll().isEmpty());
    }

    @Test
    void testGetAllWhenTableIsEmpty() {
        assertTrue(product.getAll().isEmpty());
    }

    @Test
    void testGetAllWhenTableHasOneRow() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertEquals(1, product.getAll().size());
    }

    @Test
    void testGetAllWhenTableHasMoreRows() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        product.add("Lölő", 1000000, "HUF", "Jobbkéz", productCategory.find(1), supplier.find(1));
        assertEquals(2, product.getAll().size());
    }

    @Test
    void testWhenGetBySupplierIdIsValid() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertNotNull(product.getBySupplier(supplier.find(1).getId()));
    }

    @Test
    void testWhenGetBySupplierIdIsInValid() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertNull(product.getBySupplier(supplier.find(2).getId()));
    }

    @Test
    void testWhenGetByProductCategoryIdIsValid() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertNotNull(product.getByProductCategory(productCategory.find(1).getId()));
    }

    @Test
    void testWhenGetByProductCategoryIdIsInValid() {
        product.add("Vitya", 10000, "HUF", "A király", productCategory.find(1), supplier.find(1));
        assertNotNull(product.getByProductCategory(productCategory.find(2).getId()));
    }

}