CREATE TABLE carton_headers
(
    id                BIGSERIAL PRIMARY KEY,
    barcode           VARCHAR(255) NOT NULL UNIQUE,
    description       VARCHAR(255),
    version           INTEGER       NOT NULL,
    created_timestamp TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_timestamp TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
