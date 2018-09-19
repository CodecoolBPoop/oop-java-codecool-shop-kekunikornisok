package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.SupplierDaoJDBC;
import com.codecool.shop.jdbc.JDBCController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {
    private static JDBCController controller = JDBCController.getInstance();
    private static SupplierDao supplier = null;

    @BeforeAll
    static void setUp() {
        supplier = SupplierDaoJDBC.getInstance();
    }

    @BeforeEach
    void setUpEach() {
        controller.executeQuery(
                "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.supplier', 'id')), 1, FALSE);",
                Collections.emptyList());
    }

    @AfterEach
    void tearDownEach() {
        controller.executeQuery(
                "TRUNCATE TABLE codecool_shop_test.public.supplier CASCADE;",
                Collections.emptyList());
    }

    @Test
    void testAddDataToDatabase() {
        int tableSize = supplier.getAll().size();
        supplier.add("Heli", "Anti");
        assertEquals(tableSize + 1, supplier.getAll().size());
    }

    @Test
    void testFindInDatabaseWhenParameterIsNull() {
        assertNull(supplier.find(null));
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidInteger() {
        supplier.add("Mészi", "Lölő");
        assertNotNull(supplier.find(1));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidInteger() {
        supplier.add("Németh", "Szili");
        assertNull(supplier.find(Integer.MAX_VALUE));
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidString() {
        supplier.add("George", "Soros");
        assertNotNull(supplier.find("George"));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidString() {
        supplier.add("Szijjártó", "Pete");
        assertNull(supplier.find("Vitttyaaa"));
    }

    @Test
    void testFindInDatabaseWhenParameterIsEmptyString() {
        supplier.add("Dájcs", "Thomas");
        assertNull(supplier.find(""));
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsPresent() {
        supplier.add("Áder", "JancsiBá");
        supplier.remove(1);
        assertTrue(supplier.getAll().isEmpty());
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsNotPresent() {
        supplier.add("Diktátor", "Vitya");
        supplier.remove(Integer.MAX_VALUE);
        assertFalse(supplier.getAll().isEmpty());
    }

    @Test
    void testGetAllWhenTableIsEmpty() {
        assertTrue(supplier.getAll().isEmpty());
    }

    @Test
    void testGetAllWhenTableHasOneRow() {
    supplier.add("Jobban", "Teljesít");
        assertEquals(1, supplier.getAll().size());
    }

    @Test
    void testGetAllWhenTableHasMoreRows() {
        supplier.add("Vitya", "Isten");
        supplier.add("Narancs ország", "Narancs hatalom");
        assertEquals(2, supplier.getAll().size());
    }
}