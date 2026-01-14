package com.thegym.warehousemanagementsystem.repositories;

import com.thegym.warehousemanagementsystem.entities.Location;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByWarehouseIdAndLocationCode(Long warehouseId, String locationId);
    Optional<Location> findByLocationCode(String locationCode);

    Optional<Location> findLocationByLocationCodeAndWarehouse(String locationCode, Warehouse warehouse);
}
