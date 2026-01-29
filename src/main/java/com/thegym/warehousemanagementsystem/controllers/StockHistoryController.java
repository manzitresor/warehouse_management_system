package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.HistoryParamDto;
import com.thegym.warehousemanagementsystem.services.StockHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
@AllArgsConstructor
public class StockHistoryController {
    private final StockHistoryService stockHistoryService;

    @GetMapping
    public ResponseEntity<?> getHistory(@ModelAttribute HistoryParamDto historyParamDto) {
       var stockHistory =  stockHistoryService.getHistory(historyParamDto);
        return ResponseEntity.ok().body(stockHistory);
    }
}
