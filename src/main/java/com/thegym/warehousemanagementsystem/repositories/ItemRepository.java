package com.thegym.warehousemanagementsystem.repositories;

import com.thegym.warehousemanagementsystem.entities.Item;
import com.thegym.warehousemanagementsystem.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemNumberAndLocation(String itemNumber, Location location);
    @Query("""
        SELECT i
        FROM Item i
        JOIN i.location l
        JOIN l.warehouse w
        WHERE w.warehouseNumber = :warehouseNumber
    """)
    List<Item> findAllByWarehouseNumber(String warehouseNumber);
    @Query("""
    SELECT i 
        FROM Item i
        JOIN i.location l
        JOIN l.warehouse w
        WHERE w.warehouseNumber = :warehouseNumber
        AND i.itemNumber = :itemNumber
        AND l.locationCode = :locationCode
    """)
    Optional<Item> findByWarehouseNumberAndItemNumberAndLocationCode(String warehouseNumber, String itemNumber, String locationCode);
}
