package com.thegym.warehousemanagementsystem.services;

import com.thegym.warehousemanagementsystem.dtos.UpdateWarehouseRequestDto;
import com.thegym.warehousemanagementsystem.dtos.WarehouseRequestDto;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public Warehouse createWarehouse(WarehouseRequestDto warehouseRequestDto) {
        if (warehouseRepository.existsWarehouseByWarehouseNumber(warehouseRequestDto.getWarehouseNumber())) {
            throw new ConflictException("Warehouse with number " + warehouseRequestDto.getWarehouseNumber() + " already exists.");
        }

        var warehouse = new Warehouse();
        warehouse.setWarehouseNumber(warehouseRequestDto.getWarehouseNumber());
        warehouse.setName(warehouseRequestDto.getName());

        return warehouseRepository.save(warehouse);
    }

    @Transactional
    public Warehouse update(Long id, UpdateWarehouseRequestDto dto) {

        Warehouse warehouse = warehouseRepository.findById(id).orElse(null);
        if (warehouse == null) {
            throw new ResourceNotFoundException("Warehouse not found");
        }

        warehouse.setName(dto.getName());
        warehouse.setActive(dto.getActive());
        return warehouse;
    }

}
