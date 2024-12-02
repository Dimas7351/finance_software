CREATE TABLE transaction
(
    transaction_id SERIAL PRIMARY KEY,
    user_id        INTEGER REFERENCES users (user_id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    category_id    INTEGER,
    amount        NUMERIC(12, 2),
    type          VARCHAR(10),
    date          TIMESTAMP
);