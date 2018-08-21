DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS shopping_cart;
DROP TABLE IF EXISTS shopping_cart_products;
DROP TABLE IF EXISTS shipping_address;


CREATE TABLE supplier (
  id            SERIAL       PRIMARY KEY NOT NULL,
  name          VARCHAR(255) UNIQUE      NOT NULL,
  description   VARCHAR(255)             NOT NULL
);

CREATE TABLE product_category (
  id            SERIAL       PRIMARY KEY NOT NULL,
  name          VARCHAR(255) UNIQUE      NOT NULL,
  description   VARCHAR(255)             NOT NULL,
  department    VARCHAR(255)             NOT NULL
);

CREATE TABLE product (
  id                  SERIAL       PRIMARY KEY   NOT NULL,
  name                VARCHAR(255) UNIQUE        NOT NULL,
  description         VARCHAR(255)               NOT NULL,
  defaultPrice        DOUBLE PRECISION           NOT NULL,
  currencyString      VARCHAR(255)               NOT NULL,
  supplier_id         INTEGER                    NOT NULL,
  product_category_id INTEGER                    NOT NULL
);

CREATE TABLE users (
  id               SERIAL       PRIMARY KEY   NOT NULL,
  email_address    VARCHAR(255) UNIQUE        NOT NULL,
  password         VARCHAR(255)               NOT NULL,
  first_name       VARCHAR(255)               NOT NULL,
  last_name        VARCHAR(255)               NOT NULL,
  country          VARCHAR(255)               NOT NULL,
  city             VARCHAR(255)               NOT NULL,
  address          VARCHAR(255)               NOT NULL,
  zip_code         VARCHAR(255)               NOT NULL,
  is_shipping_same BOOLEAN                    NOT NULL
);

CREATE TABLE shopping_cart (
  id        SERIAL  PRIMARY KEY  NOT NULL,
  user_id   INTEGER              NOT NULL,
  time      TIMESTAMP            NOT NULL,
  status    VARCHAR(255)         NOT NULL
);

CREATE TABLE shopping_cart_products (
  shopping_card_id  INTEGER   NOT NULL,
  product_id        INTEGER   NOT NULL,
  amount            INTEGER   NOT NULL
);

CREATE TABLE shipping_address (
  user_id   INTEGER       NOT NULL,
  country   VARCHAR(255)  NOT NULL,
  city      VARCHAR(255)  NOT NULL,
  address   VARCHAR(255)  NOT NULL,
  zip_code  VARCHAR(255)  NOT NULL
);

ALTER TABLE product
  ADD CONSTRAINT fk_product_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier(id);

ALTER TABLE product
  ADD CONSTRAINT fk_product_product_category_id FOREIGN KEY (product_category_id) REFERENCES product_category(id);

ALTER TABLE shopping_cart
  ADD CONSTRAINT fk_shopping_cart_user_id FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE shopping_cart_products
  ADD CONSTRAINT fk_shopping_cart_products_shopping_cart_id FOREIGN KEY (shopping_card_id) REFERENCES shopping_cart(id);

ALTER TABLE shopping_cart_products
  ADD CONSTRAINT fk_shopping_cart_products_product_id FOREIGN KEY (product_id) REFERENCES product(id);

ALTER TABLE shipping_address
  ADD CONSTRAINT fk_shipping_address_user_id FOREIGN KEY (user_id) REFERENCES users(id);

INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'ThimAir', 'A legjobb levego szeles e fold kereken');
INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'SAiry', 'XXX');
INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'OlivAir', 'XXX');
INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'ScrumMastAir', 'YYY');
INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'KallAir', 'XXX');
INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'AirAction', 'XXX');
INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'PolgarMestAir', 'XXX');
INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'TejutrendszAir', 'XXX');
INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'KoshAir', 'XXX');
INSERT INTO supplier (id, name, description) VALUES (DEFAULT, 'TripAir', 'XXX');

INSERT INTO product_category (id, name, description, department) VALUES (DEFAULT, 'UrbanAir', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.', 'Air');
INSERT INTO product_category (id, name, description, department) VALUES (DEFAULT, 'MountAir', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.', 'Air');
INSERT INTO product_category (id, name, description, department) VALUES (DEFAULT, 'SummAir', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.', 'Air');
INSERT INTO product_category (id, name, description, department) VALUES (DEFAULT, 'HypAir', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.', 'Air');
INSERT INTO product_category (id, name, description, department) VALUES (DEFAULT, 'MeditAirrane', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.', 'Air');
INSERT INTO product_category (id, name, description, department) VALUES (DEFAULT, 'CountrAir', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.', 'Air');
INSERT INTO product_category (id, name, description, department) VALUES (DEFAULT, 'WintAir', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.', 'Air');

INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'Blaha', 'Egyenest Pest bugyraibol, a 2-es metro poklabol, 2017-es termes', 49.9, 'USD', 5, 1);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'NewYorkAir', 'A forro beton es a csajok izzadsagszaga, ez New York', 119, 'USD', 1, 2);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'Avonley Road 23', 'Ritkasag, nehezen befoghato levego, limitalt', 99, 'USD', 1, 1);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'CzigarettAir', 'Kek Camel dohany, piros Smoking cigipapir es rizlaszuro = hmm ', 69, 'USD', 1, 1);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'Alpesi Heves Jeges', 'Friss jeges, hevesen, szelesen', 25, 'USD', 10, 2);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'Badacsonyi Hamvas', 'Balaton feloli, eszak-nyugati, 2016-os, diofa hordoban erlelt', 29, 'USD', 7, 2);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'Csomolungma Csucsa', '10 embereletbe kerul egy ilyen levego beszerzese', 999, 'USD', 6, 2);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, '4-6 Potlo Summer Edt.', 'Mindenki kedvence, limitalt ideig', 250, 'USD', 5, 3);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'Epsilon Eridion b', 'Darth Vader hozta, gyujtoi darab', 499, 'USD', 8, 4);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'Osiris Limited Edt.', 'Az ordog hata mogul', 666, 'USD', 8, 4);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'Rub''al Hali Dry', 'Egy palmafa mellol, 10 szem homokkal, 7 puttonyos', 89, 'USD', 3, 5);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'NyariKonyha', 'Egy kis leveskocka illata szall a levegoben, nagymama meglegyintette a kopenyet.', 9, 'USD', 4, 6);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'John Deere', 'Az erzes amikor belepsz a faluba es erzed a szalonna gane kombot. Kezd feljonni a palinka Ye', 50, 'USD', 2, 6);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES (DEFAULT, 'Santa Claus Is Coming', 'Egynyari termek, ketszer', 24, 'USD', 6, 7);


INSERT INTO users (id, email_address, password, first_name, last_name, country, city, address, zip_code, is_shipping_same) VALUES (DEFAULT, 'admin@gmail.com', 'admin', 'Nagy', 'Árpi', 'Hungary', 'Bugyi', 'Faszom street 7', '3012', false);

INSERT INTO shopping_cart (id, user_id, "time", status) VALUES (DEFAULT, 1, '2018-9-20 14:51:00', 'checked');
INSERT INTO shopping_cart (id, user_id, "time", status) VALUES (DEFAULT, 1, '2018-9-20 14:55:00', 'in_cart');


INSERT INTO shopping_cart_products (shopping_card_id, product_id, amount) VALUES (1, 1, 1);
INSERT INTO shopping_cart_products (shopping_card_id, product_id, amount) VALUES (1, 2, 3);

INSERT INTO shipping_address (user_id, country, city, address, zip_code) VALUES (1, 'Hungary', 'Ondód', 'Pina street 14', '3015');

