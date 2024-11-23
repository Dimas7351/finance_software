create table email_verification_tokens(
    token_id BIGSERIAL PRIMARY KEY,
    token VARCHAR UNIQUE NOT NULL ,
    created_at TIMESTAMP NOT NULL ,
    expires_at TIMESTAMP  NOT NULL,
    confirmed_at TIMESTAMP,
    user_id BIGINT NOT NULL REFERENCES users(user_id)
);