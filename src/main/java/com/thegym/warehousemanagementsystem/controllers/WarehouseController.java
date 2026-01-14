package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.requestDto.LocationRequestDto;
import com.thegym.warehousemanagementsystem.dtos.responseDto.LocationResponseDto;
import com.thegym.warehousemanagementsystem.dtos.requestDto.UpdateWarehouseRequestDto;
import com.thegym.warehousemanagementsystem.dtos.requestDto.WarehouseRequestDto;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import com.thegym.warehousemanagementsystem.services.LocationService;
import com.thegym.warehousemanagementsystem.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LocationResponseDto> createLocation(@PathVariable Long warehouseId, @RequestBody LocationRequestDto locationRequestDto) {
        var location = locationService.create(warehouseId, locationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

}
