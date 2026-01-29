package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.requestDto.ReceiveItemRequestDto;
import com.thegym.warehousemanagementsystem.dtos.responseDto.ItemResponseDto;
import com.thegym.warehousemanagementsystem.entities.*;
import com.thegym.warehousemanagementsystem.enums.Action;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    public ItemResponseDto create(ReceiveItemRequestDto requestDto) {
        var warehouse = warehouseRepository.findByWarehouseNumber(requestDto.getWarehouseNumber())
                .orElseThrow(()->new ResourceNotFoundException("Warehouse not found"));

        if(Boolean.FALSE.equals(warehouse.getActive())){
            throw new ConflictException("Warehouse "+warehouse.getWarehouseNumber()+" is inactive.");
        }

        var sscc = ssccRepository.findBySscc(requestDto.getSscc())
                .orElseThrow(()->new ResourceNotFoundException("Sscc not found"));

        if(sscc.getReceivedTimestamp() != null ) {
            throw new ConflictException("This Item has been received.");
        }


        var cartonHeader = sscc.getCartonHeader();
        var location = locationRepository.findByLocationCode(requestDto.getLocationCode())
                .orElseThrow(()->new ResourceNotFoundException("Location not found"));

        var existingItemOpt = itemRepository.findByItemNumberAndLocation(cartonHeader.getBarcode(), location);
        if(existingItemOpt.isPresent()) {
            Item existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            setReceivedTimeStamp(sscc);

            saveHistory(cartonHeader, warehouse, sscc, existingItem);
            itemRepository.save(existingItem);
            return itemToDto(existingItem);
        }

        Item item = setItem(cartonHeader, location, sscc);
        saveHistory(cartonHeader, warehouse, sscc, item);
        itemRepository.save(item);
        return itemToDto(item);
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

    private ItemResponseDto itemToDto(Item item) {
        return new ItemResponseDto(
                item.getItemNumber(),
                item.getLocation().getLocationCode(),
                item.getCartonHeader().getBarcode(),
                item.getCartonHeader().getDescription(),
                item.getQuantity()
        );
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
