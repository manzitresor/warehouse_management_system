create table warehouses
(
    id                   bigint generated always as identity primary key,
    warehouse_number     varchar(50) not null unique,
    name                 varchar(255) not null,
    active               boolean not null default true,
    version              integer not null,
    created_timestamp    timestamp not null default current_timestamp,
    updated_timestamp    timestamp not null default current_timestamp
);