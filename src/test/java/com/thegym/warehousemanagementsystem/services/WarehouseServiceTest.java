package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.UpdateWarehouseRequestDto;
import com.thegym.warehousemanagementsystem.dtos.WarehouseRequestDto;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    private Warehouse warehouse;

    @BeforeEach
    public void setup() {
        warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setName("Warehouse test");
        warehouse.setWarehouseNumber("120");
    }

    @Test
    @DisplayName("Should Create warehouse successful")
    public void warehouse_create_warehouse_successful () {
        WarehouseRequestDto warehouseRequestDto = new WarehouseRequestDto("Warehouse test","120");

        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);
        Warehouse savedWarehouse = warehouseService.create(warehouseRequestDto);

        Assertions.assertNotNull(savedWarehouse);
        Assertions.assertEquals("Warehouse test", savedWarehouse.getName());
        Assertions.assertEquals("120", savedWarehouse.getWarehouseNumber());
    }

    @Test
    @DisplayName("Should Update warehouse Successful")
    public void warehouse_update_warehouse_successful () {
        UpdateWarehouseRequestDto updateWarehouseRequestDto = new UpdateWarehouseRequestDto("main warehouse updated",false);

        when(warehouseRepository.findById(warehouse.getId())).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        Warehouse updatedWarehouse = warehouseService.update(warehouse.getId(), updateWarehouseRequestDto);
        Assertions.assertNotNull(updatedWarehouse);

        verify(warehouseRepository, times(1)).findById(warehouse.getId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when warehouse id does not exist")
    public void warehouse_update_throws_exception_when_warehouseNotFound() {
        Long nonExistentId = 99L;
        UpdateWarehouseRequestDto updateDto = new UpdateWarehouseRequestDto("New Name", true);

        when(warehouseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> warehouseService.update(nonExistentId, updateDto));
        verify(warehouseRepository, never()).save(any(Warehouse.class));
    }

}
