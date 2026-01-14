package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.requestDto.ReceiveItemRequestDto;
import com.thegym.warehousemanagementsystem.dtos.responseDto.ItemResponseDto;
import com.thegym.warehousemanagementsystem.entities.Item;
import com.thegym.warehousemanagementsystem.services.ReceiveItemService;
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
    private final ReceiveItemService receiveItemService;

    @PostMapping()
    public ResponseEntity<ItemResponseDto> createSscc(@RequestBody ReceiveItemRequestDto receiveItemRequestDto) {
        ItemResponseDto item = receiveItemService.create(receiveItemRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
