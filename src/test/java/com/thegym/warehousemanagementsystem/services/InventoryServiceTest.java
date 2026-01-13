package com.thegym.warehousemanagementsystem.services;

import com.thegym.warehousemanagementsystem.dtos.ItemListResponseDto;
import com.thegym.warehousemanagementsystem.dtos.ItemResponseDto;
import com.thegym.warehousemanagementsystem.entities.CartonHeader;
import com.thegym.warehousemanagementsystem.entities.Item;
import com.thegym.warehousemanagementsystem.entities.Location;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.ItemRepository;
import com.thegym.warehousemanagementsystem.repositories.LocationRepository;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Warehouse warehouse;
    private Location location;
    private Item item;
    private CartonHeader cartonHeader;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        warehouse.setWarehouseNumber("W1");

        location = new Location();
        location.setLocationCode("1-1-1");
        location.setWarehouse(warehouse);

        cartonHeader = new CartonHeader();
        cartonHeader.setBarcode("40123450");
        cartonHeader.setDescription("Test carton");

        item = new Item();
        item.setItemNumber("40123456");
        item.setQuantity(5);
        item.setLocation(location);
        item.setCartonHeader(cartonHeader);
    }

    @Test
    @DisplayName("Should return all items for a valid warehouse")
    void inventory_getAllItems_Success() {

        when(warehouseRepository.findByWarehouseNumber("W1")).thenReturn(Optional.of(warehouse));
        when(itemRepository.findAllByWarehouseNumber("W1")).thenReturn(List.of(item));

        List<ItemListResponseDto> result = inventoryService.getAllItems("W1");

        assertEquals(1, result.size());
        assertEquals("40123456", result.getFirst().getItemNumber());
        assertEquals(5, result.getFirst().getQuantity());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when warehouse does not exist")
    void inventory_getAllItems_WarehouseNotFound() {
        when(warehouseRepository.findByWarehouseNumber("NON-EXISTENT")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.getAllItems("NON-EXISTENT"));
    }

    @Test
    @DisplayName("Should return specific item details successfully")
    void inventory_getItem_Success() {
        when(warehouseRepository.findByWarehouseNumber("W1")).thenReturn(Optional.of(warehouse));
        when(locationRepository.findByLocationCode("1-1-1")).thenReturn(Optional.of(location));
        when(itemRepository.findByWarehouseNumberAndItemNumberAndLocationCode("W1", "40123456", "1-1-1"))
                .thenReturn(Optional.of(item));

        ItemResponseDto result = inventoryService.getItem("W1", "40123456", "1-1-1");

        assertNotNull(result);
        assertEquals("40123456", result.getItemNumber());
        assertEquals("40123450", result.getCartonBarcode());
        assertEquals(5, result.getQuantity());
    }

}