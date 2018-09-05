package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.GregorianCalendar;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();

        //setting up a new supplier
        Supplier thimAir = new Supplier(1, "ThimAir", "A legjobb levego szeles e fold kereken");
        supplierDataStore.add(thimAir.getName(), thimAir.getDescription());
        Supplier sAiry = new Supplier(2, "SAiry", "XXX");
        supplierDataStore.add(sAiry.getName(), sAiry.getDescription());
        Supplier olivAir = new Supplier(3, "OlivAir", "XXX");
        supplierDataStore.add(olivAir.getName(), olivAir.getDescription());
        Supplier scrumMastAir = new Supplier(4, "ScrumMastAir", "YYY");
        supplierDataStore.add(scrumMastAir.getName(), scrumMastAir.getDescription());
        Supplier kallAir = new Supplier(5, "KallAir", "XXX");
        supplierDataStore.add(kallAir.getName(), kallAir.getDescription());
        Supplier airAction = new Supplier(6, "AirAction", "XXX");
        supplierDataStore.add(airAction.getName(), airAction.getDescription());
        Supplier polgarMestAir = new Supplier(7, "PolgarMestAir", "XXX");
        supplierDataStore.add(polgarMestAir.getName(), polgarMestAir.getDescription());
        Supplier tejutrendszAir = new Supplier(8, "TejutrendszAir", "XXX");
        supplierDataStore.add(tejutrendszAir.getName(), tejutrendszAir.getDescription());
        Supplier koshAir = new Supplier(9, "KoshAir", "XXX");
        supplierDataStore.add(koshAir.getName(), koshAir.getDescription());
        Supplier tripAir = new Supplier(10, "TripAir", "XXX");
        supplierDataStore.add(tripAir.getName(), tripAir.getDescription());

        //setting up a new product category
        ProductCategory urbanAir = new ProductCategory(1, "UrbanAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(urbanAir.getName(), urbanAir.getDescription(), urbanAir.getDepartment());
        ProductCategory mountAir = new ProductCategory(2, "MountAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(mountAir.getName(), mountAir.getDescription(), mountAir.getDepartment());
        ProductCategory summAir = new ProductCategory(3, "SummAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(summAir.getName(), summAir.getDescription(), summAir.getDepartment());
        ProductCategory hypAir = new ProductCategory(4, "HypAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(hypAir.getName(), hypAir.getDescription(), hypAir.getDepartment());
        ProductCategory meditAirien = new ProductCategory(5, "MeditAirrane", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(meditAirien.getName(), meditAirien.getDescription(), meditAirien.getDepartment());
        ProductCategory countAir = new ProductCategory(6, "CountrAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(countAir.getName(), countAir.getDescription(), countAir.getDepartment());
        ProductCategory wintAir = new ProductCategory(7, "WintAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(wintAir.getName(), wintAir.getDescription(), wintAir.getDepartment());

        //setting up products and printing it
        productDataStore.add("Blaha", 49.9f, "USD", "Egyenest Pest bugyraibol, a 2-es metro poklabol, 2017-es termes", urbanAir, kallAir);
        productDataStore.add("NewYorkAir", 119, "USD", "A forro beton es a csajok izzadsagszaga, ez New York", urbanAir, tripAir);
        productDataStore.add("Avonley Road 23", 99, "USD", "Ritkasag, nehezen befoghato levego, limitalt", urbanAir, thimAir);
        productDataStore.add("CzigarettAir", 69, "USD", "Kek Camel dohany, piros Smoking cigipapir es rizlaszuro = hmm ", urbanAir, thimAir);
        productDataStore.add("Alpesi Heves Jeges", 25, "USD", "Friss jeges, hevesen, szelesen", mountAir, tripAir);
        productDataStore.add("Badacsonyi Hamvas", 29, "USD", "Balaton feloli, eszak-nyugati, 2016-os, diofa hordoban erlelt", mountAir, polgarMestAir);
        productDataStore.add("Csomolungma Csucsa", 999, "USD", "10 embereletbe kerul egy ilyen levego beszerzese", mountAir, airAction);
        productDataStore.add("4-6 Potlo Summer Edt.", 250, "USD", "Mindenki kedvence, limitalt ideig", summAir, kallAir);
        productDataStore.add("Epsilon Eridion b", 499, "USD", "Darth Vader hozta, gyujtoi darab", hypAir, tejutrendszAir);
        productDataStore.add("Osiris Limited Edt.", 666, "USD", "Az ordog hata mogul", hypAir, tejutrendszAir);
        productDataStore.add("Rub'al Hali Dry", 89, "USD", "Egy palmafa mellol, 10 szem homokkal, 7 puttonyos", meditAirien, olivAir);
        productDataStore.add("NyariKonyha", 9, "USD", "Egy kis leveskocka illata szall a levegoben, nagymama meglegyintette a kopenyet.", countAir, scrumMastAir);
        productDataStore.add("John Deere", 50, "USD", "Az erzes amikor belepsz a faluba es erzed a szalonna gane kombot. Kezd feljonni a palinka Ye", countAir, sAiry);
        productDataStore.add("Santa Claus Is Coming", 24, "USD", "Egynyari termek, ketszer", wintAir, airAction);

        //setting up starter shopping cart
        shoppingCartDataStore.add(1, new GregorianCalendar(2018, 8, 24).getTime());

    }
}
