package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.LocationRequestDto;
import com.thegym.warehousemanagementsystem.dtos.UpdateWarehouseRequestDto;
import com.thegym.warehousemanagementsystem.dtos.WarehouseRequestDto;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.services.LocationService;
import com.thegym.warehousemanagementsystem.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/warehouses")
@AllArgsConstructor
public class WarehouseController {
    private final LocationService locationService;
    private WarehouseService warehouseService;


    @PostMapping()
    public ResponseEntity<Warehouse> createWarehouse(@Valid @RequestBody WarehouseRequestDto request) {
        var warehouse = warehouseService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWarehouse(@PathVariable Long id, @Valid @RequestBody UpdateWarehouseRequestDto request) {
        var warehouse = warehouseService.update(id, request);
        return ResponseEntity.ok().body(warehouse);
    }

    @PostMapping("/{warehouseId}/locations")
    public ResponseEntity<?> createLocation(@PathVariable Long warehouseId, @RequestBody LocationRequestDto locationRequestDto) {
        var location = locationService.create(warehouseId, locationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleDuplicateWarehouseException(ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Conflict",
                "message", e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }
}
