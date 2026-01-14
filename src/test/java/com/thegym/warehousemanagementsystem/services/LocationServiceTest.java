package com.thegym.warehousemanagementsystem.services;

import com.thegym.warehousemanagementsystem.dtos.requestDto.LocationRequestDto;
import com.thegym.warehousemanagementsystem.entities.Location;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.LocationRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {
    @Mock
    private LocationRepository locationRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private LocationService locationService;

    private Warehouse warehouse;
    private Location location;

    @BeforeEach
    public void setup() {
        warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setName("Main Warehouse");
        warehouse.setWarehouseNumber("4");

        location = new Location();
        location.setRow(1);
        location.setSection(2);
        location.setShelf(4);
        location.setWarehouse(warehouse);


        String locationCode = location.getRow() + "-" + location.getSection() + "-" + location.getShelf();
        location.setLocationCode(locationCode);
    }

    @Test
    @DisplayName("Should create location successful")
    public void location_createLocation_success() {
        LocationRequestDto locationRequestDto = new LocationRequestDto(1,2,4);
        when(warehouseRepository.findById(warehouse.getId())).thenReturn(Optional.of(warehouse));
        when(locationRepository.save(any(Location.class))).thenReturn(location);


        Location savedLocation = locationService.create(warehouse.getId(),locationRequestDto);

        Assertions.assertNotNull(savedLocation);
        Assertions.assertEquals(savedLocation.getId(),location.getId());
    }


    @Test
    @DisplayName("Should throw if warehouse not found")
    public void location_createLocation_not_found() {
        LocationRequestDto locationRequestDto = new LocationRequestDto(1,2,4);
        Long warehouseId = 5L;
        when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,() -> locationService.create(warehouseId,locationRequestDto));
    }

    @Test
    @DisplayName("Should throw if location code already exists")
    public void location_createLocation_fail() {
        LocationRequestDto locationRequestDto = new LocationRequestDto(1,2,4);
        String locationCode = "1-2-4";
        when(warehouseRepository.findById(warehouse.getId())).thenReturn(Optional.of(warehouse));
        when(locationRepository.existsByWarehouseIdAndLocationCode(warehouse.getId(), locationCode)).thenReturn(true);

        Assertions.assertThrows(ConflictException.class, () -> locationService.create(warehouse.getId(),locationRequestDto));
    }

    @Test
    @DisplayName("Should create location code ")
    public void location_createLocation_code_success() {
        LocationRequestDto locationRequestDto = new LocationRequestDto(1,2,4);
        String  locationCode = "1-2-4";
        when(warehouseRepository.findById(warehouse.getId())).thenReturn(Optional.of(warehouse));
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        Location savedLocation = locationService.create(warehouse.getId(),locationRequestDto);

        Assertions.assertEquals(locationCode, savedLocation.getLocationCode());

    }
}
