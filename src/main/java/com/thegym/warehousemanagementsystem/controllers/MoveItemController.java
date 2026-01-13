package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.ItemResponseDto;
import com.thegym.warehousemanagementsystem.dtos.MoveItemRequestDto;
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
    private final MoveItemService moveItemService;

    @PostMapping()
    public ResponseEntity<ItemResponseDto> moveItem(@RequestBody MoveItemRequestDto moveItemRequestDto) {
        var item = moveItemService.moveItem(moveItemRequestDto);
        return ResponseEntity.ok().body(item);
    }
}
