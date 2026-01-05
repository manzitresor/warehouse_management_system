package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.WarehouseRequestDto;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import com.thegym.warehousemanagementsystem.exceptions.DuplicateWarehouseException;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import com.thegym.warehousemanagementsystem.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WarehouseController {
    private WarehouseService warehouseService;


    @PostMapping("/warehouses")
    public ResponseEntity<Warehouse> getWarehouse(@Valid @RequestBody WarehouseRequestDto warehouseRequestDto) {
        var warehouse = warehouseService.createWarehouse(warehouseRequestDto);
        return ResponseEntity.ok().body(warehouse);
    }

    @ExceptionHandler(DuplicateWarehouseException.class)
    public  ResponseEntity<?> handleProductNotFoundException(DuplicateWarehouseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Conflict",
                "message", e.getMessage()));
    }

}
