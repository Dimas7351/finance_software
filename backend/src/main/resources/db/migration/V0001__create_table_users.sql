CREATE TABLE users
(
    user_id       SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    phone_number  VARCHAR(18),
    date_of_birth DATE,
    password      VARCHAR(255) NOT NULL,
    currency      VARCHAR(10) DEFAULT 'USD'
);

