CREATE TABLE locations
(
    id BIGSERIAL PRIMARY KEY,
    row INTEGER NOT NULL,
    section INTEGER NOT NULL,
    shelf INTEGER NOT NULL,
    location_code VARCHAR(100) NOT NULL,
    warehouse_id BIGINT NOT NULL,
    version INTEGER NOT NULL,
    created_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_warehouse_location UNIQUE (warehouse_id, location_code),

    CONSTRAINT fk_locations_warehouse
        FOREIGN KEY (warehouse_id)
            REFERENCES warehouses (id)
);
