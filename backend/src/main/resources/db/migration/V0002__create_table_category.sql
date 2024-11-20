CREATE TABLE category
(
    category_id SERIAL PRIMARY KEY,
    user_id     INTEGER      NOT NULL REFERENCES users (user_id)
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    name        VARCHAR(100) NOT NULL,
    color       VARCHAR(20)
);
