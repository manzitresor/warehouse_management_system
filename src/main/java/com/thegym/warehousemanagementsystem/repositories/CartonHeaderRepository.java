package com.thegym.warehousemanagementsystem.repositories;

import com.thegym.warehousemanagementsystem.entities.CartonHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CartonHeaderRepository extends JpaRepository<CartonHeader,Long> {
    boolean existsCartonHeadersByBarcode(String barcode);
}
