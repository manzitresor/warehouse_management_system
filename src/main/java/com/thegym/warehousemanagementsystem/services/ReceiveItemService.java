package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.ReceiveItemRequestDto;
import com.thegym.warehousemanagementsystem.entities.*;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@AllArgsConstructor
public class ReceiveItemService {
    private final StockHistoryRepository stockHistoryRepository;
    private LocationRepository locationRepository;
    private WarehouseRepository warehouseRepository;
    private SsccRepository ssccRepository;
    private ItemRepository itemRepository;


    @Transactional
    public Item create(ReceiveItemRequestDto requestDto) {
        var warehouse = warehouseRepository.findByWarehouseNumber(requestDto.getWarehouseNumber()).orElseThrow(()->new ResourceNotFoundException("Warehouse not found"));
        if(Boolean.FALSE.equals(warehouse.getActive())){
            throw new ConflictException("Warehouse "+warehouse.getWarehouseNumber()+" is inactive.");
        }

        var sscc = ssccRepository.findBySscc(requestDto.getSscc()).orElseThrow(()->new ResourceNotFoundException("Sscc not found"));
        if(sscc.getReceivedTimestamp() != null ) {
            throw new ConflictException("This Item has been received.");
        }


        var cartonHeader = sscc.getCartonHeader();
        var location = locationRepository.findByLocationCode(requestDto.getLocationCode()).orElseThrow(()->new ResourceNotFoundException("Location not found"));

        var existingItemOpt = itemRepository.findByItemNumberAndLocation(cartonHeader.getBarcode(), location);
        if(existingItemOpt.isPresent()) {
            Item existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            setReceivedTimeStamp(sscc);

            saveHistory(cartonHeader, warehouse, sscc, existingItem);
            return itemRepository.save(existingItem);
        }

        Item item = setItem(cartonHeader, location, sscc);
        saveHistory(cartonHeader, warehouse, sscc, item);

        return itemRepository.save(item);
    }

    private Item setItem(CartonHeader cartonHeader, Location location, Sscc sscc) {
        Item item = new Item();
        item.setItemNumber(cartonHeader.getBarcode());
        item.setQuantity(1);
        item.setCartonHeader(cartonHeader);
        item.setLocation(location);
        setReceivedTimeStamp(sscc);
        return item;
    }

    private void saveHistory(CartonHeader cartonHeader, Warehouse warehouse, Sscc sscc, Item item) {
        StockHistory stockHistory = new StockHistory();
        stockHistory.setAction(Action.RECEIVE);
        stockHistory.setItemNumber(cartonHeader.getBarcode());
        stockHistory.setWarehouseNumber(warehouse.getWarehouseNumber());
        stockHistory.setSscc(sscc.getSscc());
        stockHistory.setItemNumber(item.getItemNumber());
        stockHistory.setQuantityChange(1);
        stockHistoryRepository.save(stockHistory);
    }

    private void setReceivedTimeStamp(Sscc sscc) {
        sscc.setReceivedTimestamp(Instant.now());
        ssccRepository.save(sscc);
    }
}
