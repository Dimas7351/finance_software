CREATE TABLE bank_integration
(
    integration_id SERIAL PRIMARY KEY,
    user_id       INTEGER      NOT NULL REFERENCES users (user_id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    bank_name      VARCHAR(100) NOT NULL,
    key_api        VARCHAR(255) NOT NULL,
    status        BOOLEAN DEFAULT true
);