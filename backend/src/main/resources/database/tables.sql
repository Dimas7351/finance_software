CREATE TABLE users (
                         userID SERIAL,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         phone_number VARCHAR(15),
                         date_of_birth DATE,
                         password VARCHAR(255) NOT NULL,
                         currency VARCHAR(10) DEFAULT 'USD',
                         PRIMARY KEY(userID)
);


CREATE TABLE category (
                          categoryID SERIAL,
                          userID INTEGER NOT NULL,
                          name VARCHAR(100) NOT NULL,
                          color VARCHAR(20),
                            PRIMARY KEY(categoryID),
                            FOREIGN KEY(userID) REFERENCES users(userID)
                                ON UPDATE NO ACTION ON DELETE NO ACTION
);


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

CREATE TABLE bankIntegration (
                                 integrationID SERIAL,
                                 userID INTEGER NOT NULL,
                                 bankName VARCHAR(100) NOT NULL,
                                 keyAPI VARCHAR(255) NOT NULL,
                                 status BOOLEAN DEFAULT true,
                                   PRIMARY KEY(integrationID),
                                   FOREIGN KEY(userID) REFERENCES users(userID)
                                        ON UPDATE NO ACTION ON DELETE NO ACTION
);

SELECT * FROM users;