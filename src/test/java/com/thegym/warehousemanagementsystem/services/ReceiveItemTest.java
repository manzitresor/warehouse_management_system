package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.dtos.ReceiveItemRequestDto;
import com.thegym.warehousemanagementsystem.entities.*;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiveItemTest {
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private SsccRepository ssccRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CartonHeaderRepository cartonHeaderRepository;


    @InjectMocks
    private ReceiveItemService receiveItemService;

    private Warehouse warehouse;
    private Location location;
    private CartonHeader cartonHeader;
    private Sscc sscc;
    private Item item;

    @BeforeEach
    public void setup() {
        warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setName("Warehouse test");
        warehouse.setWarehouseNumber("4");
        warehouse.setActive(true);

        location = new Location();
        location.setRow(1);
        location.setSection(2);
        location.setShelf(4);
        location.setWarehouse(warehouse);

        cartonHeader = new CartonHeader();
        cartonHeader.setBarcode("40123451");
        cartonHeader.setDescription("Box with white t-shirts");
        cartonHeader.setId(1L);

        sscc = new Sscc();
        sscc.setSscc("003871234567890122");
        sscc.setReceivedTimestamp(null);
        sscc.setCartonHeader(cartonHeader);

        item = new Item();
        item.setItemNumber(cartonHeader.getBarcode());
        item.setQuantity(1);
        item.setLocation(location);
        item.setCartonHeader(cartonHeader);
    }

    @Test
    @DisplayName("Should create item")
    public void should_create_item() {
        ReceiveItemRequestDto requestDto = new ReceiveItemRequestDto("4", "003871234567890122", "1-2-4");

        when(warehouseRepository.findByWarehouseNumber("4")).thenReturn(warehouse);
        when(ssccRepository.findBySscc("003871234567890122")).thenReturn(sscc);
        when(locationRepository.findByLocationCode("1-2-4")).thenReturn(location);
        when(itemRepository.findByItemNumberAndLocation(any(), any())).thenReturn(Optional.empty());
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item savedItem = receiveItemService.create(requestDto);

        Assertions.assertNotNull(savedItem);
        Assertions.assertEquals(cartonHeader.getBarcode(), savedItem.getItemNumber());
    }

    @Test
    @DisplayName("Should increment quantity if already exists")
    public void should_increment_quantity_if_item_exists(){
        ReceiveItemRequestDto requestDto = new ReceiveItemRequestDto("4", "003871234567890122", "1-2-4");

        when(warehouseRepository.findByWarehouseNumber("4")).thenReturn(warehouse);
        when(ssccRepository.findBySscc("003871234567890122")).thenReturn(sscc);
        when(locationRepository.findByLocationCode("1-2-4")).thenReturn(location);
        when(itemRepository.findByItemNumberAndLocation(cartonHeader.getBarcode(),location)).thenReturn(Optional.of(item));

        item.setQuantity(item.getQuantity() + 1);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item savedItem = receiveItemService.create(requestDto);
        Assertions.assertNotNull(savedItem);
        Assertions.assertEquals(item.getQuantity(), savedItem.getQuantity());
    }

    @Test
    @DisplayName("Should not create sscc twice")
    public void should_throw_if_sscc_already_received() {
        ReceiveItemRequestDto requestDto = new ReceiveItemRequestDto("4", "003871234567890122", "1-2-4");

        when(warehouseRepository.findByWarehouseNumber("4")).thenReturn(warehouse);

        sscc.setReceivedTimestamp(Instant.now());
        when(ssccRepository.findBySscc("003871234567890122")).thenReturn(sscc);

        Assertions.assertThrows(ConflictException.class,()-> receiveItemService.create(requestDto));
    }

}
