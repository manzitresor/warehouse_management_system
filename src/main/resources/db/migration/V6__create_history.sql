CREATE TABLE stock_history (
   id                 BIGSERIAL PRIMARY KEY,
   action             VARCHAR(255) NOT NULL,
   item_number        VARCHAR(255)      NOT NULL,
   quantity_change    INTEGER      NOT NULL,
   from_location_code VARCHAR(255),
   to_location_code   VARCHAR(255),
   warehouse_number   VARCHAR(255) NOT NULL,
   sscc               VARCHAR(255),
   created_timestamp  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
