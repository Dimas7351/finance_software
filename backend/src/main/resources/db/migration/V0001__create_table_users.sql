CREATE TABLE users(
    user_id       BIGSERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    phone_number  VARCHAR(18),
    date_of_birth DATE,
    is_verified BOOLEAN DEFAULT FALSE,
    password      VARCHAR(255) NOT NULL,
    currency      VARCHAR(3)
    upload_type   VARCHAR(10) CHECK (upload_type IN ('fromBank', 'manual'))
    bank_name     VARCHAR(25)
);

