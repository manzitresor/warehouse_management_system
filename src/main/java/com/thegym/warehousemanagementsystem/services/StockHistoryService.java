package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.HistoryParamDto;
import com.thegym.warehousemanagementsystem.entities.StockHistory;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.StockHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;

    public List<StockHistory> getHistory(HistoryParamDto historyParamDto) {
        List<StockHistory> history = stockHistoryRepository.findHistory(historyParamDto.getWarehouseNumber(),historyParamDto.getItemNumber(),historyParamDto.getLocationCode());
        if (history == null) {
            throw new ResourceNotFoundException("History Not Found");
        }

        return history.stream().toList();
    }
}
