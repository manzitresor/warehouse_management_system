package com.thegym.warehousemanagementsystem.services;

import com.thegym.warehousemanagementsystem.dtos.WarehouseRequestDto;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import com.thegym.warehousemanagementsystem.exceptions.DuplicateWarehouseException;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public Warehouse createWarehouse(WarehouseRequestDto warehouseRequestDto) {
        if(warehouseRepository.existsWarehouseByWarehouseNumber(warehouseRequestDto.getWarehouseNumber())){
            throw new DuplicateWarehouseException("Warehouse with number " + warehouseRequestDto.getWarehouseNumber() + " already exists.");
        }

        var warehouse = new Warehouse();
        warehouse.setWarehouseNumber(warehouseRequestDto.getWarehouseNumber());
        warehouse.setName(warehouseRequestDto.getName());
        warehouse.setVersion(1);

        return warehouseRepository.save(warehouse);
    }
}
