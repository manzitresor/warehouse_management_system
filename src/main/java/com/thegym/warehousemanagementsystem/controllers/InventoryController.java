package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.services.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses/{warehouseNumber}/items")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping()
    public ResponseEntity<?> getItemsByWarehouse(@PathVariable String warehouseNumber) {
        var items = inventoryService.getAllItems(warehouseNumber);
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/{itemNumber}")
    public ResponseEntity<?> getItem(@PathVariable String warehouseNumber, @PathVariable String itemNumber, @RequestParam String locationCode) {
        var  item = inventoryService.getItem(warehouseNumber, itemNumber, locationCode);
        return ResponseEntity.ok().body(item);
    }
}
