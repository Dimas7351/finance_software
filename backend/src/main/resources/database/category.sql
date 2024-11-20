CREATE TABLE category (
    categoryID SERIAL,
    userID INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(20),
    PRIMARY KEY(categoryID),
    FOREIGN KEY(userID) REFERENCES users(userID)
        ON UPDATE NO ACTION ON DELETE NO ACTION
);
