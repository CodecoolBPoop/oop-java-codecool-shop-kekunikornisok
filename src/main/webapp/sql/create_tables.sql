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
  defaultPrice        DOUBLE                     NOT NULL,
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
  ordered   BOOLEAN              NOT NULL
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

INSERT INTO supplier (id, name, description) VALUES ('ThimAir', 'A legjobb levego szeles e fold kereken');
INSERT INTO supplier (id, name, description) VALUES ('TripAir', 'XXX');

INSERT INTO product_category (id, name, description, department) VALUES ('UrbanAir', 'Air', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');
INSERT INTO product_category (id, name, description, department) VALUES ('CountrAir', 'Air', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');

INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES ('NewYorkAir', 119, 'USD', 'A forro beton es a csajok izzadsagszaga, ez New York', 1, 2);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES ('Avonley Road 23', 99, 'USD', 'Ritkasag, nehezen befoghato levego, limitalt', 1, 1);
INSERT INTO product (id, name, description, defaultPrice, currencyString, supplier_id, product_category_id) VALUES ('CzigarettAir', 69, 'USD', 'Kek Camel dohany, piros Smoking cigipapir es rizlaszuro = hmm', 1, 1);

INSERT INTO users (id, email_address, password, first_name, last_name, country, city, address, zip_code, is_shipping_same) VALUES ('admin@gmail.com', 'admin', 'Nagy', 'Árpi', 'Hungary', 'Bugyi', 'Faszom street 7', '3012', false);

INSERT INTO shopping_cart (id, user_id, "time", ordered) VALUES (1, '2018-9-20 14:51:00', false);

INSERT INTO shopping_cart_products (shopping_card_id, product_id, amount) VALUES (1, 1, 1);
INSERT INTO shopping_cart_products (shopping_card_id, product_id, amount) VALUES (1, 2, 3);

INSERT INTO shipping_address (user_id, country, city, address, zip_code) VALUES (1, 'Hungary', 'Ondód', 'Pina street 14', '3015');

