package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.CartonHeaderRequestDto;
import com.thegym.warehousemanagementsystem.dtos.SsccRequestDto;
import com.thegym.warehousemanagementsystem.entities.CartonHeader;
import com.thegym.warehousemanagementsystem.entities.Sscc;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.CartonHeaderRepository;
import com.thegym.warehousemanagementsystem.repositories.SsccRepository;
import com.thegym.warehousemanagementsystem.services.CartonHeaderService;
import com.thegym.warehousemanagementsystem.services.SsccService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/carton-headers")
@AllArgsConstructor
public class CartonHeaderController {

    private final CartonHeaderService cartonHeaderService;
    private final SsccService ssccService;

    @PostMapping()
    public ResponseEntity<?> createCartonHeader(@Valid @RequestBody CartonHeaderRequestDto cartonHeaderRequestDto) {
        CartonHeader cartonHeader = cartonHeaderService.create(cartonHeaderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartonHeader);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCartonHeader(@Valid @PathVariable Long id, @RequestBody CartonHeaderRequestDto cartonHeaderRequestDto) {
        CartonHeader cartonHeader = cartonHeaderService.update(id, cartonHeaderRequestDto);
        return ResponseEntity.ok().body(cartonHeader);
    }

    @PostMapping("/{id}/ssccs")
    public ResponseEntity<?> createSsccs(@Valid @PathVariable Long id, @RequestBody SsccRequestDto createSssccRequiredDto){
        Sscc sscc = ssccService.create(id,createSssccRequiredDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sscc);
    }




    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }
}
