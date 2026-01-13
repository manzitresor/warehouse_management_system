package com.thegym.warehousemanagementsystem.repositories;


import com.thegym.warehousemanagementsystem.entities.CartonHeader;
import com.thegym.warehousemanagementsystem.entities.Item;
import com.thegym.warehousemanagementsystem.entities.Location;
import com.thegym.warehousemanagementsystem.entities.Warehouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CartonHeaderRepository cartonHeaderRepository;

    private Item item;
    private Location location;
    private CartonHeader cartonHeader;



    @BeforeEach
    void setup() {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseNumber("w1");
        warehouse.setName("Main Warehouse");
        warehouse.setActive(true);
        warehouse = warehouseRepository.saveAndFlush(warehouse);

        Location loc = new Location();
        loc.setRow(1);
        loc.setSection(1);
        loc.setShelf(1);
        loc.setLocationCode("1-1-1");
        loc.setWarehouse(warehouse);
        location = locationRepository.saveAndFlush(loc);

        CartonHeader header = new CartonHeader();
        header.setBarcode("40123450");
        header.setDescription("Test carton");
        cartonHeader = cartonHeaderRepository.saveAndFlush(header);

        item = new Item();
        item.setItemNumber("40123456");
        item.setQuantity(5);
        item.setLocation(location);
        item.setCartonHeader(cartonHeader);
    }

    @Test
    @DisplayName("Should create Item successful")
    public void item_create_successful() {
        Item savedItem = itemRepository.saveAndFlush(item);
        Assertions.assertNotNull(savedItem);
    }

    @Test
    @DisplayName("Should item be unique in location")
    public void item_create_constraints_unique() {
        itemRepository.saveAndFlush(item);

        Item duplicate = new Item();
        duplicate.setItemNumber("40123456");
        duplicate.setQuantity(2);
        duplicate.setLocation(location);
        duplicate.setCartonHeader(cartonHeader);

        Assertions.assertThrows(DataIntegrityViolationException.class,  () -> itemRepository.saveAndFlush(duplicate));
    }

    @Test
    @DisplayName("Should throw if quantity is not positive")
    public void item_create_constraints_negative() {
        item.setQuantity(-5);
        Assertions.assertThrows(DataIntegrityViolationException.class,  () -> itemRepository.saveAndFlush(item));
    }

    @Test
    @DisplayName("Should get List of items in warehouse")
    public void item_getAll_ReturnItems() {
        itemRepository.saveAndFlush(item);

        List<Item> items = itemRepository.findAllByWarehouseNumber("w1");
        Assertions.assertNotNull(items);
    }

    @Test
    @DisplayName("Should get List of items in warehouse")
    public void item_getOne_By_WarehouseNumber_ItemNumber_LocationCode_ReturnItem() {
        itemRepository.saveAndFlush(item);

        var returnItem = itemRepository.findByWarehouseNumberAndItemNumberAndLocationCode("w1","40123456","1-1-1");
        Assertions.assertNotNull(returnItem);
        Assertions.assertEquals("40123456", returnItem.get().getItemNumber());
    }

}
