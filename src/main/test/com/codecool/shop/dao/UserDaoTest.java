package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.UserDaoJDBC;
import com.codecool.shop.jdbc.JDBCController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private static JDBCController controller = JDBCController.getInstance();
    private static UserDao user = null;

    @BeforeAll
    static void setUp() {
        user = UserDaoJDBC.getInstance();
    }

    @BeforeEach
    void setUpEach() {
        controller.executeQuery(
        "SELECT setval((SELECT pg_get_serial_sequence('codecool_shop_test.public.users', 'id')), 1, FALSE);",
            Collections.emptyList());
    }

    @AfterEach
    void tearDownEach() {
        controller.executeQuery(
        "TRUNCATE TABLE codecool_shop_test.public.users CASCADE;",
            Collections.emptyList());
    }

    @Test
    void testAddDataToDatabaseOnly2Parameter() {
        int tableSize = user.getAll().size();
        user.add("Vitya", "Isten");
        assertEquals(tableSize + 1, user.getAll().size());
    }

    @Test
    void testAddDataToDatabaseAllParameter() {
        int tableSize = user.getAll().size();
        user.add("Vitya", "Isten", "Lölö", "a", "jobb",
                "keze", "neki", "He", false);
        assertEquals(tableSize + 1, user.getAll().size());
    }

    @Test
    void testSetTableEmptyStringInput() {
        user.add("Vitya", "Vitya");
        user.setTable(1,"", "Isten", "Lölö", "a", "jobb",
                "keze");
        assertEquals("", user.find(1).getFirstName());
    }

    @Test
    void testSetTableEmptyValidInput() {
        user.add("Vitya", "Vitya");
        user.setTable(1,"Lölö", "Isten", "Lölö", "a", "jobb",
                "keze");
        assertEquals("Lölö", user.find(1).getFirstName());
    }

    @Test
    void testFindInDatabaseWhenParameterIsNull() {
        assertNull(user.find(null));
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidInteger() {
        user.add("Vitya", "Isten");
        assertNotNull(user.find(1));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidInteger() {
        user.add("Vitya", "Isten");
        assertNull(user.find(Integer.MAX_VALUE));
    }

    @Test
    void testFindInDatabaseWhenParameterIsValidString() {
        user.add("Vitya", "Isten");
        assertNotNull(user.find("Vitya"));
    }

    @Test
    void testFindInDatabaseWhenParameterIsInvalidString() {
        user.add("Vitya", "Isten");
        assertNull(user.find("Vitttyaaa"));
    }

    @Test
    void testFindInDatabaseWhenParameterIsEmptyString() {
        user.add("Vitya", "Isten");
        assertNull(user.find(""));
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsPresent() {
        user.add("Vitya", "Isten");
        user.remove(1);
        assertTrue(user.getAll().isEmpty());
    }

    @Test
    void testRemoveDataFromDatabaseWhenParameterIsNotPresent() {
        user.add("Vitya", "Isten");
        user.remove(Integer.MAX_VALUE);
        assertFalse(user.getAll().isEmpty());
    }

    @Test
    void testGetAllWhenTableIsEmpty() {
        assertTrue(user.getAll().isEmpty());
    }

    @Test
    void testGetAllValidInput() {
        user.add("Vitya", "Király");
        assertEquals(1, user.getAll().size());
    }

    @Test
    void testGetAllInvalidInput() {
        user.add("Lölö", "az Isten");
        user.add("Áder", "Jancsibá");
        assertEquals(2, user.getAll().size());
    }

    @Test
    void testGetEmailsWhenTableIsEmpty() {
        assertTrue(user.getEmails().isEmpty());
    }

    @Test
    void testGetEmailsValidInput() {
        user.add("Vitya", "Király");
        assertEquals(1, user.getEmails().size());
    }

    @Test
    void testGetEmailsInvalidInput() {
        user.add("Lölö", "az Isten");
        user.add("Áder", "Jancsibá");
        assertEquals(2, user.getEmails().size());
    }

    @Test
    void testValidRegisterNullInput() {
        assertFalse(user.validRegister(null, "Vitya", "Vitya"));
    }

    @Test
    void testValidRegisterEmptyStringInput() {
        assertFalse(user.validRegister("", "", "Vitya"));
    }

    @Test
    void testValidRegisterPasswordsDoNotMatch() {
        assertFalse(user.validRegister("Vitya", "Vityaaaaaa", "Vitya"));
    }

    @Test
    void testValidRegisterValidInput() {
        assertTrue(user.validRegister("Vitya", "Vitya", "Vitya"));
    }

    @Test
    void testValidRegisterEmailAlreadyInUse() {
        user.add("Vitya", "Vitya");
        assertFalse(user.validRegister("Vitya", "Vitya", "Vitya"));
    }

    @Test
    void testValidLoginPasswordsDoNotMatch() {
        user.add("Vitya", "Vitya");
        assertFalse(user.validLogin( "Vitya", "Vityaaaaaa"));
    }

    @Test
    void testValidLoginNullInput() {
        user.add("Vitya", "Vitya");
        assertFalse(user.validLogin( null, "Vitya"));
    }

    @Test
    void testValidLoginEmptyStringInput() {
        user.add("Vitya", "Vitya");
        assertFalse(user.validLogin( "", "Vitya"));
    }

    @Test
    void testValidLoginValidInput() {
        user.add("Vitya", "Vitya");
        assertTrue(user.validLogin( "Vitya", "Vitya"));
    }

}