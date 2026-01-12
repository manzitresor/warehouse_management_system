package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.ReceiveItemRequestDto;
import com.thegym.warehousemanagementsystem.entities.Item;
import com.thegym.warehousemanagementsystem.entities.Sscc;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.ItemRepository;
import com.thegym.warehousemanagementsystem.repositories.LocationRepository;
import com.thegym.warehousemanagementsystem.repositories.SsccRepository;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@AllArgsConstructor
public class ReceiveItemService {
    private LocationRepository locationRepository;
    private WarehouseRepository warehouseRepository;
    private SsccRepository ssccRepository;
    private ItemRepository itemRepository;


    @Transactional
    public Item create(ReceiveItemRequestDto requestDto) {
        var warehouse = warehouseRepository.findByWarehouseNumber(requestDto.getWarehouseNumber()).orElse(null);
        if (warehouse == null) {
            throw new ResourceNotFoundException("Warehouse not found or is inactive.");
        }

        if(Boolean.FALSE.equals(warehouse.getActive())){
            throw new ConflictException("Warehouse "+warehouse.getWarehouseNumber()+" is inactive.");
        }

        var sscc = ssccRepository.findBySscc(requestDto.getSscc()).orElse(null);
        if (sscc == null ) {
            throw new ResourceNotFoundException("Sscc not found");
        }

        if(sscc.getReceivedTimestamp() != null ) {
            throw new ConflictException("This Item is has been received.");
        }


        var cartonHeader = sscc.getCartonHeader();
        var location = locationRepository.findByLocationCode(requestDto.getLocationCode()).orElse(null);
        if(location == null) {
            throw new ResourceNotFoundException("Location not found.");
        }

        var existingItemOpt = itemRepository.findByItemNumberAndLocation(cartonHeader.getBarcode(), location);
        if(existingItemOpt.isPresent()) {
            Item  existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            setReceivedTimeStamp(sscc);
            return itemRepository.save(existingItem);
        }

        Item item = new Item();
        item.setItemNumber(cartonHeader.getBarcode());
        item.setQuantity(1);
        item.setCartonHeader(cartonHeader);
        item.setLocation(location);
        setReceivedTimeStamp(sscc);

        return itemRepository.save(item);
    }

    private void setReceivedTimeStamp(Sscc sscc) {
        sscc.setReceivedTimestamp(Instant.now());
        ssccRepository.save(sscc);
    }
}
