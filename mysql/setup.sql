create database IF NOT EXISTS product;

use product;

CREATE TABLE IF NOT EXISTS product(
   id int auto_increment primary key,
   store_id int,
   `name` varchar(255),
   brand varchar(255),
   price double,
   color varchar(50),
   created_date datetime,
   updated_date datetime,
   quantity int
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS cart(
   id int primary key,
   updated_date datetime
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS cart_product(
   cart_id int ,
   product_id int,
   quantity int,
   primary key (cart_id, product_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `order`(
   id int auto_increment primary key,
   user_id int,
   status int,
   total_price double,
   updated_date datetime,
   created_date datetime
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS order_product(
   order_id int,
   product_id int,
   quantity int,
   price double,
   primary key (order_id, product_id)
) ENGINE=INNODB;