package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.responseDto.ItemResponseDto;
import com.thegym.warehousemanagementsystem.dtos.requestDto.MoveItemRequestDto;
import com.thegym.warehousemanagementsystem.producers.MoveItemProducer;
import com.thegym.warehousemanagementsystem.services.MoveItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/move")
@AllArgsConstructor
public class MoveItemController {
    private MoveItemProducer moveItemProducer;

    @PostMapping()
    public ResponseEntity<ItemResponseDto> moveItem(@RequestBody MoveItemRequestDto moveItemRequestDto) {
        moveItemProducer.initiateMove(moveItemRequestDto);
        return ResponseEntity.ok().build();
    }
}
