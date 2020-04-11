--DROP TABLE IF EXISTS USERS_TABLE;
--DROP TABLE IF EXISTS PRODUCTS;
DROP TABLE IF EXISTS BILLONARIES;
DROP TABLE IF EXISTS USERSTABLE;

CREATE TABLE IF NOT EXISTS USERS_TABLE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_type INT NOT NULL,
    username VARCHAR(250) NOT NULL,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    direction VARCHAR(250) NOT NULL,
    city VARCHAR(250) NOT NULL,
    postal_code VARCHAR(250) NOT NULL,
    password_hash BINARY(64) NOT NULL,
    email VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS PRODUCTS (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    seller_username VARCHAR(254) NOT NULL,
    product_name VARCHAR(250) NOT NULL,
    description VARCHAR(250) NOT NULL,
    image VARBINARY(MAX) NOT NULL,
    state INT NOT NULL,
    postal_code VARCHAR(250) NOT NULL
);


INSERT INTO USERS_TABLE (user_type, username, first_name, last_name, direction, city, postal_code, password_hash, email) VALUES
  ('0', 'client', 'Aliko', 'Dangote', 'Billionaire Industrialist','4','dcfs','098f6bcd4621d373cade4e832627b4f6','asda'), --password: test
    ('1', 'botiga', 'Aliko', 'Dangote', 'Billionaire Industrialist','4','dcfs','098f6bcd4621d373cade4e832627b4f6','asda');
--  ('Bill', 'Gates', 'Billionaire Tech Entrepreneur','7','dcfds','darscfsa','asd4a'),
--  ('Folrunsho', 'Alakija', 'Billionaire Oil Magnate','9','ddcfs','dayscfsa','asuda');
