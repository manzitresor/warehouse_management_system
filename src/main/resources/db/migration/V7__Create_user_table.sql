CREATE TABLE users
(
    id   BIGSERIAL PRIMARY KEY,
    role varchar(255) default 'USER' not null,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_timestamp  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_timestamp TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);