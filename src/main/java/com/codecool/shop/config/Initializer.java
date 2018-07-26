package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier thimAir = new Supplier("ThimAir", "A legjobb levego szeles e fold kereken");
        supplierDataStore.add(thimAir);
        Supplier sAiry = new Supplier("SAiry", "XXX");
        supplierDataStore.add(sAiry);
        Supplier olivAir = new Supplier("OlivAir", "XXX");
        supplierDataStore.add(olivAir);
        Supplier scrumMastAir = new Supplier("ScrumMastAir", "YYY");
        supplierDataStore.add(scrumMastAir);
        Supplier kallAir = new Supplier("KallAir", "XXX");
        supplierDataStore.add(kallAir);
        Supplier airAction = new Supplier("AirAction", "XXX");
        supplierDataStore.add(airAction);
        Supplier polgarMestAir = new Supplier("PolgarMestAir", "XXX");
        supplierDataStore.add(polgarMestAir);
        Supplier tejutrendszAir = new Supplier("TejutrendszAir", "XXX");
        supplierDataStore.add(tejutrendszAir);
        Supplier koshAir = new Supplier("KoshAir", "XXX");
        supplierDataStore.add(koshAir);
        Supplier tripAir = new Supplier("TripAir", "XXX");
        supplierDataStore.add(tripAir);

        //setting up a new product category
        ProductCategory urbanAir = new ProductCategory("UrbanAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(urbanAir);
        ProductCategory mountAir = new ProductCategory("MountAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(mountAir);
        ProductCategory summAir = new ProductCategory("SummAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(summAir);
        ProductCategory hypAir = new ProductCategory("HypAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(hypAir);
        ProductCategory meditAirien = new ProductCategory("MeditAirrane", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(meditAirien);
        ProductCategory countAir = new ProductCategory("CountrAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(countAir);
        ProductCategory wintAir = new ProductCategory("WintAir", "Air", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(wintAir);

        //setting up products and printing it
        productDataStore.add(new Product("Blaha", 49.9f, "USD", "Egyenest Pest bugyraibol, a 2-es metro poklabol, 2017-es termes", urbanAir, kallAir));
        productDataStore.add(new Product("NewYorkAir", 119, "USD", "A forro beton es a csajok izzadsagszaga, ez New York", urbanAir, tripAir));
        productDataStore.add(new Product("Avonley Road 23", 99, "USD", "Ritkasag, nehezen befoghato levego, limitalt", urbanAir, thimAir));
        productDataStore.add(new Product("CzigarettAir", 69, "USD", "Kek Camel dohany, piros Smoking cigipapir es rizlaszuro = hmm ", urbanAir, thimAir));
        productDataStore.add(new Product("Alpesi Heves Jeges", 25, "USD", "Friss jeges, hevesen, szelesen", mountAir, tripAir));
        productDataStore.add(new Product("Badacsonyi Hamvas", 29, "USD", "Balaton feloli, eszak-nyugati, 2016-os, diofa hordoban erlelt", mountAir, polgarMestAir));
        productDataStore.add(new Product("Csomolungma Csucsa", 999, "USD", "10 embereletbe kerul egy ilyen levego beszerzese", mountAir, airAction));
        productDataStore.add(new Product("4-6 Potlo Summer Edt.", 250, "USD", "Mindenki kedvence, limitalt ideig", summAir, kallAir));
        productDataStore.add(new Product("Epsilon Eridion b", 499, "USD", "Darth Vader hozta, gyujtoi darab", hypAir, tejutrendszAir));
        productDataStore.add(new Product("Osiris Limited Edt.", 666, "USD", "Az ordog hata mogul", hypAir, tejutrendszAir));
        productDataStore.add(new Product("Rub'al Hali Dry", 89, "USD", "Egy palmafa mellol, 10 szem homokkal, 7 puttonyos", meditAirien, olivAir));
        productDataStore.add(new Product("NyariKonyha", 9, "USD", "Egy kis leveskocka illata szall a levegoben, nagymama meglegyintette a kopenyet.", countAir, scrumMastAir));
        productDataStore.add(new Product("John Deere", 50, "USD", "Az erzes amikor belepsz a faluba es erzed a szalonna gane kombot. Kezd feljonni a palinka Ye", countAir, sAiry));
        productDataStore.add(new Product("Santa Claus Is Coming", 24, "USD", "Egynyari termek, ketszer", wintAir, airAction));

    }
}
