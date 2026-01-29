package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.responseDto.ItemResponseDto;
import com.thegym.warehousemanagementsystem.dtos.responseDto.ItemListResponseDto;
import com.thegym.warehousemanagementsystem.entities.Item;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.ItemRepository;
import com.thegym.warehousemanagementsystem.repositories.LocationRepository;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class InventoryService {

    private final WarehouseRepository warehouseRepository;
    private final ItemRepository itemRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public List<ItemListResponseDto> getAllItems(String warehouseNumber){
        var warehouse = warehouseRepository.findByWarehouseNumber(warehouseNumber).orElse(null);
        if(warehouse == null){
            throw new ResourceNotFoundException("Warehouse not found");
        }
        List<Item> items = itemRepository.findAllByWarehouseNumber(warehouse.getWarehouseNumber());
        return items.stream()
                .map(item -> new ItemListResponseDto(
                        item.getItemNumber(),
                        item.getLocation().getLocationCode(),
                        item.getQuantity()
                ))
                .toList();
    }

    public ItemResponseDto getItem(String warehouseNumber, String itemNumber, String locationCode){
        var warehouse = warehouseRepository.findByWarehouseNumber(warehouseNumber).orElse(null);
        if(warehouse == null){
            throw new ResourceNotFoundException("Warehouse not found");
        }

        var location = locationRepository.findByLocationCode(locationCode).orElse(null);
        if(location == null){
            throw new ResourceNotFoundException("Location not found");
        }

        Item item = itemRepository.findByWarehouseNumberAndItemNumberAndLocationCode(warehouseNumber,itemNumber,locationCode).orElse(null);
        if(item == null){
            throw new ResourceNotFoundException("Item not found");
        }

        return new ItemResponseDto(
                item.getItemNumber(),
                item.getLocation().getLocationCode(),
                item.getCartonHeader().getBarcode(),
                item.getCartonHeader().getDescription(),
                item.getQuantity()
        );
    }
}
