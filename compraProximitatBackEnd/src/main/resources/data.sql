--DROP TABLE IF EXISTS users_table;
CREATE TABLE IF NOT EXISTS users_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(250) NOT NULL,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    direction VARCHAR(250) NOT NULL,
    city VARCHAR(250) NOT NULL,
    postal_code VARCHAR(250) NOT NULL,
    password_hash BINARY(64) NOT NULL,
    email VARCHAR(250) NOT NULL
);


--INSERT INTO userstable (first_name, last_name, direction, city, postal_code, passwordHash, email) VALUES
--  ('Aliko', 'Dangote', 'Billionaire Industrialist','4','dcfs','dascfsa','asda'),
--  ('Bill', 'Gates', 'Billionaire Tech Entrepreneur','7','dcfds','darscfsa','asd4a'),
--  ('Folrunsho', 'Alakija', 'Billionaire Oil Magnate','9','ddcfs','dayscfsa','asuda');
