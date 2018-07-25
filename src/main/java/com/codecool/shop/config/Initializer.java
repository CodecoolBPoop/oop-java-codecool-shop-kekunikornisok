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
        productDataStore.add(new Product("Blaha", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical.", urbanAir, kallAir));
        productDataStore.add(new Product("NewYorkAir", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", urbanAir, tripAir));
        productDataStore.add(new Product("Avonley Road 23", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", urbanAir, thimAir));
        productDataStore.add(new Product("CzigarettAir", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", urbanAir, koshAir));
        productDataStore.add(new Product("Alpesi Heves Jeges", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", mountAir, tripAir));
        productDataStore.add(new Product("Badacsonyi Hamvas", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", mountAir, polgarMestAir));
        productDataStore.add(new Product("Csomolungma Csucsa", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", mountAir, airAction));
        productDataStore.add(new Product("4-6 Potlo Summer Edt.", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", summAir, kallAir));
        productDataStore.add(new Product("Epsilon Eridion b", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", hypAir, tejutrendszAir));
        productDataStore.add(new Product("Osiris Limited Edt.", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", hypAir, tejutrendszAir));
        productDataStore.add(new Product("Rub'al Hali Dry", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", meditAirien, olivAir));
        productDataStore.add(new Product("NyariKonyha", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", countAir, scrumMastAir));
        productDataStore.add(new Product("John Deere", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", countAir, sAiry));
        productDataStore.add(new Product("Santa Claus Is Coming To Town", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", wintAir, airAction));

    }
}
