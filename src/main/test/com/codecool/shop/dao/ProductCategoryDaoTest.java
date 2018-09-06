package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoJDBC;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    private ProductCategoryDao productCategory = null;

    @BeforeAll
    void setup() {
        productCategory = ProductCategoryDaoJDBC.getInstance();
    }

}