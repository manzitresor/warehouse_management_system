CREATE TABLE ssccs
(
    id                 BIGSERIAL PRIMARY KEY,
    sscc               VARCHAR(255) NOT NULL UNIQUE,
    carton_header_id   BIGINT      NOT NULL,
    received_timestamp TIMESTAMP NULL,
    created_timestamp  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_timestamp  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sscc_carton_header
        FOREIGN KEY (carton_header_id)
            REFERENCES carton_headers (id)
);
