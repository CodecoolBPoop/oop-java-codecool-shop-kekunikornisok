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
  id                    SERIAL       PRIMARY KEY   NOT NULL,
  name                  VARCHAR(255) UNIQUE        NOT NULL,
  description           VARCHAR(255)               NOT NULL,
  default_price         DOUBLE PRECISION           NOT NULL,
  currency_string       VARCHAR(255)               NOT NULL,
  supplier_id           INTEGER                    NOT NULL,
  product_category_id   INTEGER                    NOT NULL
);

CREATE TABLE users (
  id                  SERIAL       PRIMARY KEY   NOT NULL,
  email_address       VARCHAR(255) UNIQUE        NOT NULL,
  password            VARCHAR(255)               NOT NULL,
  first_name          VARCHAR(255)               NOT NULL,
  last_name           VARCHAR(255)               NOT NULL,
  country             VARCHAR(255)               NOT NULL,
  city                VARCHAR(255)               NOT NULL,
  address             VARCHAR(255)               NOT NULL,
  zip_code            VARCHAR(255)               NOT NULL,
  is_shipping_same    BOOLEAN                    NOT NULL
);

CREATE TABLE shopping_cart (
  id        SERIAL  PRIMARY KEY  NOT NULL,
  user_id   INTEGER              NOT NULL,
  time      TIMESTAMP            NOT NULL,
  status    VARCHAR(255)         NOT NULL
);

CREATE TABLE shopping_cart_products (
  shopping_cart_id  INTEGER   NOT NULL,
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
  ADD CONSTRAINT fk_shopping_cart_products_shopping_cart_id FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id);

ALTER TABLE shopping_cart_products
  ADD CONSTRAINT fk_shopping_cart_products_product_id FOREIGN KEY (product_id) REFERENCES product(id);

ALTER TABLE shipping_address
  ADD CONSTRAINT fk_shipping_address_user_id FOREIGN KEY (user_id) REFERENCES users(id);