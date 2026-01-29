package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.requestDto.ReceiveItemRequestDto;
import com.thegym.warehousemanagementsystem.producers.ReceiveItemProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receive")
@AllArgsConstructor
public class ReceiveItemController {
    private final ReceiveItemProducer receiveItemProducer;

    @PostMapping()
    public ResponseEntity<?> createSscc(@RequestBody ReceiveItemRequestDto receiveItemRequestDto) {
        receiveItemProducer.initialCheck(receiveItemRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
