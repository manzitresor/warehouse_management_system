package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.ItemResponseDto;
import com.thegym.warehousemanagementsystem.dtos.MoveItemRequestDto;
import com.thegym.warehousemanagementsystem.entities.*;
import com.thegym.warehousemanagementsystem.exceptions.InvalidInputException;
import com.thegym.warehousemanagementsystem.repositories.ItemRepository;
import com.thegym.warehousemanagementsystem.repositories.LocationRepository;
import com.thegym.warehousemanagementsystem.repositories.StockHistoryRepository;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoveItemServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private StockHistoryRepository stockHistoryRepository;

    @InjectMocks
    private MoveItemService moveItemService;

    private Warehouse warehouse;
    private Location fromLocation;
    private Location toLocation;
    private Item fromItem;
    private Item toItem;
    private MoveItemRequestDto requestDto;
    private CartonHeader cartonHeader;

    @BeforeEach
    public void setup() {
        warehouse = new Warehouse();
        warehouse.setWarehouseNumber("W1");
        warehouse.setActive(true);

        fromLocation = new Location();
        fromLocation.setLocationCode("1-1-1");
        fromLocation.setWarehouse(warehouse);

        toLocation = new Location();
        toLocation.setLocationCode("1-1-2");
        toLocation.setWarehouse(warehouse);

        cartonHeader = new CartonHeader();
        cartonHeader.setBarcode("40123451");
        cartonHeader.setDescription("Box with white t-shirts");
        cartonHeader.setId(1L);

        fromItem = new Item();
        fromItem.setItemNumber(cartonHeader.getBarcode());
        fromItem.setQuantity(10);
        fromItem.setLocation(fromLocation);
        fromItem.setCartonHeader(cartonHeader);

        toItem = new Item();
        toItem.setItemNumber(cartonHeader.getBarcode());
        toItem.setQuantity(5);
        toItem.setLocation(toLocation);
        toItem.setCartonHeader(cartonHeader);

        requestDto = new MoveItemRequestDto("W1","40123451","1-1-1","1-1-2",3);

    }

    @Test
    @DisplayName("Should successfully move items and update quantities")
    void moveItem_Success() {

        when(warehouseRepository.findByWarehouseNumber("W1")).thenReturn(Optional.of(warehouse));
        when(locationRepository.findLocationByLocationCodeAndWarehouse("1-1-1", warehouse)).thenReturn(Optional.of(fromLocation));
        when(locationRepository.findLocationByLocationCodeAndWarehouse("1-1-2", warehouse)).thenReturn(Optional.of(toLocation));
        when(itemRepository.findByItemNumberAndLocation("40123451", fromLocation)).thenReturn(Optional.of(fromItem));
        when(itemRepository.findByItemNumberAndLocation("40123451", toLocation)).thenReturn(Optional.of(toItem));

        ItemResponseDto response = moveItemService.moveItem(requestDto);

        Assertions.assertEquals(7, fromItem.getQuantity());
        Assertions.assertEquals(8, toItem.getQuantity());

        verify(itemRepository, times(2)).save(any(Item.class));
        verify(stockHistoryRepository, times(1)).save(any(StockHistory.class));
    }

    @Test
    @DisplayName("Should throw exception if warehouse is not active")
    void moveItem_InactiveWarehouse() {
        warehouse.setActive(false);
        when(warehouseRepository.findByWarehouseNumber("W1")).thenReturn(Optional.of(warehouse));

        Assertions.assertThrows(InvalidInputException.class, () -> moveItemService.moveItem(requestDto));
    }

    @Test
    @DisplayName("Should throw exception if quantity is insufficient")
    void moveItem_InsufficientQuantity() {
        requestDto.setQuantity(50);

        when(warehouseRepository.findByWarehouseNumber("W1")).thenReturn(Optional.of(warehouse));
        when(locationRepository.findLocationByLocationCodeAndWarehouse(anyString(), any())).thenReturn(Optional.of(fromLocation));

        Assertions.assertThrows(InvalidInputException.class, () -> moveItemService.moveItem(requestDto));
    }

}
