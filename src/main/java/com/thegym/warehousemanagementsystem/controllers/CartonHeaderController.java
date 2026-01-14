package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.responseDto.CartonHeaderRequestDto;
import com.thegym.warehousemanagementsystem.dtos.responseDto.CartonHeaderResponseDto;
import com.thegym.warehousemanagementsystem.dtos.requestDto.SsccRequestDto;
import com.thegym.warehousemanagementsystem.entities.Sscc;
import com.thegym.warehousemanagementsystem.services.CartonHeaderService;
import com.thegym.warehousemanagementsystem.services.SsccService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carton-headers")
@AllArgsConstructor
public class CartonHeaderController {

    private final CartonHeaderService cartonHeaderService;
    private final SsccService ssccService;

    @PostMapping()
    public ResponseEntity<CartonHeaderResponseDto> createCartonHeader(@Valid @RequestBody CartonHeaderRequestDto cartonHeaderRequestDto) {
        CartonHeaderResponseDto cartonHeader = cartonHeaderService.create(cartonHeaderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartonHeader);
    }

    @PutMapping("/{barcode}")
    public ResponseEntity<CartonHeaderResponseDto> updateCartonHeader(@Valid @PathVariable String barcode, @RequestBody CartonHeaderRequestDto cartonHeaderRequestDto) {
        CartonHeaderResponseDto cartonHeader = cartonHeaderService.update(barcode, cartonHeaderRequestDto);
        return ResponseEntity.ok().body(cartonHeader);
    }

    @PostMapping("/{barcode}/ssccs")
    public ResponseEntity<?> createSsccs(@Valid @PathVariable String barcode, @RequestBody SsccRequestDto createSssccRequiredDto){
        Sscc sscc = ssccService.create(barcode,createSssccRequiredDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sscc);
    }

}
