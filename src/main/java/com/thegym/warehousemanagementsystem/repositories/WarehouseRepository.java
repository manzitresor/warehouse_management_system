package com.thegym.warehousemanagementsystem.repositories;

import com.thegym.warehousemanagementsystem.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    boolean existsWarehouseByWarehouseNumber(String warehouseNumber);
    Optional<Warehouse> findByWarehouseNumber(String warehouseNumber);
}
