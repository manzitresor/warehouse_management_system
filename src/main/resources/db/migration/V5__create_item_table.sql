CREATE TABLE items
(
    id                BIGSERIAL PRIMARY KEY,
    item_number       VARCHAR(255) NOT NULL,
    quantity          INTEGER      NOT NULL CHECK (quantity >= 0),
    version           INTEGER      NOT NULL DEFAULT 0,
    location_id       BIGINT       NOT NULL,
    carton_header_id  BIGINT       NOT NULL,
    created_timestamp TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_timestamp TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_items_item_number_location
        UNIQUE (item_number, location_id),

    CONSTRAINT fk_items_location
        FOREIGN KEY (location_id)
            REFERENCES locations (id),

    CONSTRAINT fk_items_carton_header
        FOREIGN KEY (carton_header_id)
            REFERENCES carton_headers (id)
);
