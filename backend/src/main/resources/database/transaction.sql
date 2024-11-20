
CREATE TABLE transaction (
    transactionID SERIAL,
    userID INTEGER NOT NULL,
    categoryID INTEGER,
    amount NUMERIC(12,2) NOT NULL,
    type VARCHAR(10) CHECK(type IN ('income' , 'expense')),
    date TIMESTAMP NOT NULL,
    PRIMARY KEY(transactionID),
    FOREIGN KEY(userID) REFERENCES users(userID)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY(categoryID) REFERENCES category(categoryID)
        ON UPDATE NO ACTION ON DELETE NO ACTION
);