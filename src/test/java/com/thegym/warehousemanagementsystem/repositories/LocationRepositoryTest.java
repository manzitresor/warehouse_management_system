package com.thegym.warehousemanagementsystem.repositories;

import com.thegym.warehousemanagementsystem.entities.Location;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LocationRepositoryTest {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    private Location location;
    private Warehouse warehouse;

    @BeforeEach
    public void setupWarehouse() {
        warehouse = new Warehouse();
        warehouse.setName("Main Warehouse");
        warehouse.setWarehouseNumber("4");
        warehouse = warehouseRepository.saveAndFlush(warehouse);


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
    public void location_create_return_success() {
        Location savedLocation = locationRepository.saveAndFlush(location);
        Assertions.assertNotNull(savedLocation);
        Assertions.assertNotNull(savedLocation.getId());
    }

    @Test
    @DisplayName("Should return true when location and warehouseId exists")
    public void existsByWarehouseIdAndLocationCode_should_return_true(){
        locationRepository.saveAndFlush(location);
        boolean exists = locationRepository.existsByWarehouseIdAndLocationCode(warehouse.getId(), location.getLocationCode());
        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("Location code should be unique")
    public void location_code_should_be_unique(){
        locationRepository.saveAndFlush(location);
        Location duplicate = new Location();
        duplicate.setRow(1);
        duplicate.setSection(2);
        duplicate.setShelf(4);
        duplicate.setLocationCode(location.getLocationCode());
        duplicate.setWarehouse(warehouse);

        Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> locationRepository.saveAndFlush(duplicate)
        );

    }

}
