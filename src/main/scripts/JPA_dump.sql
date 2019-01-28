-- MySQL Workbench Forward Engineering
-- -----------------------------------------------------
-- Schema jpa
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS jpa;

-- -----------------------------------------------------
-- Schema jpa
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS jpa DEFAULT CHARACTER SET latin1;
USE jpa;

-- -----------------------------------------------------
-- Table jpa.adres
-- -----------------------------------------------------
DROP TABLE IF EXISTS jpa.adres;

CREATE TABLE IF NOT EXISTS jpa.adres
(
  id   INT         NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
)
;

-- -----------------------------------------------------
-- Table jpa.person
-- -----------------------------------------------------
DROP TABLE IF EXISTS jpa.person;

CREATE TABLE IF NOT EXISTS jpa.person
(
  id        INT         NOT NULL AUTO_INCREMENT,
  firstname VARCHAR(45) NOT NULL,
  lastname  VARCHAR(45) NOT NULL,
  adres_fk  INT         NOT NULL,
  PRIMARY KEY (id)
)
;

-- -----------------------------------------------------
-- Table jpa.customer
-- -----------------------------------------------------
DROP TABLE IF EXISTS jpa.customer;

CREATE TABLE IF NOT EXISTS jpa.customer
(
  id              INT    NOT NULL AUTO_INCREMENT,
  customer_number INT(4) NOT NULL DEFAULT 0000,
  person_fk       INT    NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX customer_number_UNIQUE (customer_number ASC) VISIBLE
)
;

-- -----------------------------------------------------
-- Table jpa.customer.orderedAvailableProduct
-- -----------------------------------------------------
DROP TABLE IF EXISTS jpa.orderedAvailableProduct;

CREATE TABLE IF NOT EXISTS jpa.orderedAvailableProduct
(
  id             INT         NOT NULL AUTO_INCREMENT,
  name           VARCHAR(45) NOT NULL,
  price          DOUBLE      NOT NULL,
  stock_quantity INT         NOT NULL,
  rating         INT         NULL,
  PRIMARY KEY (`id`)
)
;

-- -----------------------------------------------------
-- Table jpa.ordered_product
-- -----------------------------------------------------
DROP TABLE IF EXISTS jpa.ordered_product;

CREATE TABLE IF NOT EXISTS jpa.ordered_product
(
  id         INT NOT NULL AUTO_INCREMENT,
  product_fk INT NULL,
  quantity   INT NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
)
;

-- -----------------------------------------------------
-- Table jpa.delivery_method
-- -----------------------------------------------------
DROP TABLE IF EXISTS jpa.delivery_method;

CREATE TABLE IF NOT EXISTS jpa.delivery_method
(
  id     INT         NOT NULL AUTO_INCREMENT,
  method VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
)
;

-- -----------------------------------------------------
-- Table jpa.receipt
-- -----------------------------------------------------
DROP TABLE IF EXISTS jpa.receipt;

CREATE TABLE IF NOT EXISTS jpa.receipt
(
  id            INT    NOT NULL AUTO_INCREMENT,
  customer_fk   INT    NOT NULL,
  delivery_fk   INT    NOT NULL,
  total_price   DOUBLE NOT NULL DEFAULT 0,
  purchase_date DATE   NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE
)
;

-- -----------------------------------------------------
-- Table jpa.order_detail
-- -----------------------------------------------------
DROP TABLE IF EXISTS jpa.order_detail;

CREATE TABLE IF NOT EXISTS jpa.order_detail
(
  receipt_fk      INT NOT NULL,
  ordered_prod_fk INT NOT NULL
)
;

ALTER TABLE jpa.person
  ADD CONSTRAINT adres_fk
    FOREIGN KEY (adres_fk)
      REFERENCES jpa.adres (id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION;

ALTER TABLE jpa.customer
  ADD CONSTRAINT person_fk
    FOREIGN KEY (person_fk)
      REFERENCES jpa.person (id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION;

ALTER TABLE jpa.ordered_product
  ADD CONSTRAINT prod_fk
    FOREIGN KEY (product_fk)
      REFERENCES jpa.orderedAvailableProduct (id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION;

ALTER TABLE jpa.receipt
  ADD CONSTRAINT customer_fk
    FOREIGN KEY (customer_fk)
      REFERENCES jpa.customer (id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  ADD CONSTRAINT delivery_fk
    FOREIGN KEY (delivery_fk)
      REFERENCES jpa.delivery_method (id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION;

ALTER TABLE jpa.order_detail
  ADD CONSTRAINT ordered_fk
    FOREIGN KEY (ordered_prod_fk)
      REFERENCES jpa.ordered_product (id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  ADD CONSTRAINT receipt_fk
    FOREIGN KEY (receipt_fk)
      REFERENCES jpa.receipt (id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION;

-- -----------------------------------------------------
-- Data for table jpa.adres
-- -----------------------------------------------------
START TRANSACTION;
USE jpa;
INSERT INTO jpa.adres (`id`, `name`)
VALUES (DEFAULT, 'Keizerstraat');
INSERT INTO jpa.adres (`id`, `name`)
VALUES (DEFAULT, 'Heliostraat');
INSERT INTO jpa.adres (`id`, `name`)
VALUES (DEFAULT, 'Uranusstraat');
INSERT INTO jpa.adres (`id`, `name`)
VALUES (DEFAULT, 'Wanicastraat');
INSERT INTO jpa.adres (`id`, `name`)
VALUES (DEFAULT, 'Grote Combe');
INSERT INTO jpa.adres (`id`, `name`)
VALUES (DEFAULT, 'Franchepanestraat');

COMMIT;

-- -----------------------------------------------------
-- Data for table jpa.person
-- -----------------------------------------------------
START TRANSACTION;
USE jpa;
INSERT INTO jpa.person (`id`, `firstname`, `lastname`, `adres_fk`)
VALUES (DEFAULT, 'Jonathan', 'Oldenstam', 2);
INSERT INTO jpa.person (`id`, `firstname`, `lastname`, `adres_fk`)
VALUES (DEFAULT, 'Maarten', 'Narain', 1);
INSERT INTO jpa.person (`id`, `firstname`, `lastname`, `adres_fk`)
VALUES (DEFAULT, 'Mitchel', 'Pawirodinomo', 4);
INSERT INTO jpa.person (`id`, `firstname`, `lastname`, `adres_fk`)
VALUES (DEFAULT, 'Romario', 'Dipopawiro', 3);
INSERT INTO jpa.person (`id`, `firstname`, `lastname`, `adres_fk`)
VALUES (DEFAULT, 'John', 'Doe', 6);
INSERT INTO jpa.person (`id`, `firstname`, `lastname`, `adres_fk`)
VALUES (DEFAULT, 'Jane', 'Woo', 5);

COMMIT;

-- -----------------------------------------------------
-- Data for table jpa.orderedAvailableProduct
-- -----------------------------------------------------
START TRANSACTION;
USE jpa;
INSERT INTO `jpa`.`orderedAvailableProduct` (`name`, `price`, `stock_quantity`, `rating`)
VALUES ('Apple iPhone X', '1100', '8', '5');
INSERT INTO `jpa`.`orderedAvailableProduct` (`name`, `price`, `stock_quantity`, `rating`)
VALUES ('Samsung Galaxy S9', '800', '10', '4');
INSERT INTO `jpa`.`orderedAvailableProduct` (`name`, `price`, `stock_quantity`, `rating`)
VALUES ('Lenovo ThinkPad T550', '1150', '9', '4');
INSERT INTO `jpa`.`orderedAvailableProduct` (`name`, `price`, `stock_quantity`, `rating`)
VALUES ('LG 49\" LED TV', '600', '15', '3');
INSERT INTO `jpa`.`orderedAvailableProduct` (`name`, `price`, `stock_quantity`, `rating`)
VALUES ('Sharp 27\" Monitor', '250', '5', '2');

COMMIT;

-- -----------------------------------------------------
-- Data for table jpa.delivery_method
-- -----------------------------------------------------

START TRANSACTION;
USE jpa;
INSERT INTO `jpa`.`delivery_method` (`method`)
VALUES ('Pick Up');
INSERT INTO `jpa`.`delivery_method` (`method`)
VALUES ('Delivery');

COMMIT;

-- -----------------------------------------------------
-- Data for table jpa.customer
-- -----------------------------------------------------

START TRANSACTION;
USE jpa;
INSERT INTO `jpa`.`customer` (`customer_number`, `person_fk`)
VALUES ('1234', '1');
INSERT INTO `jpa`.`customer` (`customer_number`, `person_fk`)
VALUES ('2345', '3');
INSERT INTO `jpa`.`customer` (`customer_number`, `person_fk`)
VALUES ('3456', '2');
INSERT INTO `jpa`.`customer` (`customer_number`, `person_fk`)
VALUES ('4567', '4');

COMMIT;

-- -----------------------------------------------------
-- Data for table jpa.ordered_product
-- -----------------------------------------------------

START TRANSACTION;
USE jpa;
INSERT INTO `jpa`.`ordered_product` (`product_fk`, `quantity`)
VALUES ('2', '3');
INSERT INTO `jpa`.`ordered_product` (`product_fk`, `quantity`)
VALUES ('1', '1');
INSERT INTO `jpa`.`ordered_product` (`product_fk`, `quantity`)
VALUES ('3', '1');
INSERT INTO `jpa`.`ordered_product` (`product_fk`, `quantity`)
VALUES ('4', '2');

COMMIT;