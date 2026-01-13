package com.thegym.warehousemanagementsystem.services;

import com.thegym.warehousemanagementsystem.dtos.ItemResponseDto;
import com.thegym.warehousemanagementsystem.dtos.MoveItemRequestDto;
import com.thegym.warehousemanagementsystem.exceptions.InvalidInputException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.ItemRepository;
import com.thegym.warehousemanagementsystem.repositories.LocationRepository;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MoveItemService {
    private final WarehouseRepository warehouseRepository;
    private final LocationRepository locationRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public ItemResponseDto moveItem(MoveItemRequestDto requestDto) {
        var warehouse = warehouseRepository.findByWarehouseNumber(requestDto.getWarehouseNumber()).orElseThrow(()->new ResourceNotFoundException("Warehouse Not Found"));
        if(Boolean.FALSE.equals(warehouse.getActive())){
            throw new InvalidInputException("Warehouse is not active.");
        }

        var fromLocation = locationRepository.findLocationByLocationCodeAndWarehouse(requestDto.getFromLocationCode(),warehouse).orElseThrow(()->new ResourceNotFoundException("Location Not Found"));
        var toLocation = locationRepository.findLocationByLocationCodeAndWarehouse(requestDto.getToLocationCode(),warehouse).orElseThrow(()->new ResourceNotFoundException("Destination Location Not Found"));

        if(fromLocation.getLocationCode().equals(toLocation.getLocationCode())){
            throw new InvalidInputException("fromLocationCode cannot be the same as toLocationCode ");
        }


        var fromItem = itemRepository.findByItemNumberAndLocation(requestDto.getItemNumber(), fromLocation).orElseThrow(()->new ResourceNotFoundException("Item Not Found"));
        if(fromItem.getQuantity() < requestDto.getQuantity()){
            throw new InvalidInputException("Insufficient quantity");
        }

        var toItem = itemRepository.findByItemNumberAndLocation(requestDto.getItemNumber(), toLocation).orElseThrow(()->new ResourceNotFoundException("Destination Item Not Found"));

        toItem.setQuantity(toItem.getQuantity() + requestDto.getQuantity());
        fromItem.setQuantity(fromItem.getQuantity() - requestDto.getQuantity());
        itemRepository.save(fromItem);
        itemRepository.save(toItem);


        return new ItemResponseDto(
                toItem.getItemNumber(),
                toItem.getLocation().getLocationCode(),
                toItem.getCartonHeader().getBarcode(),
                toItem.getCartonHeader().getDescription(),
                toItem.getQuantity()
        );
    }
}
