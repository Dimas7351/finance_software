CREATE TABLE transaction
(
    transaction_id SERIAL PRIMARY KEY,
    user_id        INTEGER        NOT NULL REFERENCES users (user_id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    category_id    INTEGER REFERENCES category (category_id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    amount        NUMERIC(12, 2) NOT NULL,
    type          VARCHAR(10) CHECK (type IN ('income', 'expense')),
    date          TIMESTAMP      NOT NULL
);