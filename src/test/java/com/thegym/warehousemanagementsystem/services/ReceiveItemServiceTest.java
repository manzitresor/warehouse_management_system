package com.thegym.warehousemanagementsystem.services;

import com.thegym.warehousemanagementsystem.KafkaEvents.ReceiveItemEvent;
import com.thegym.warehousemanagementsystem.dtos.requestDto.ReceiveItemRequestDto;
import com.thegym.warehousemanagementsystem.entities.*;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiveItemServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private SsccRepository ssccRepository;
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private ReceiveItemService receiveItemService;

    private Warehouse warehouse;
    private Sscc sscc;

    @BeforeEach
    public void setup() {
        warehouse = new Warehouse();
        warehouse.setWarehouseNumber("4");
        warehouse.setActive(true);

        sscc = new Sscc();
        sscc.setSscc("003871234567890122");
        sscc.setReceivedTimestamp(null);
    }

    @Test
    @DisplayName("Should publish Receive Intent to Kafka")
    public void should_publish_receive_intent() {
        ReceiveItemRequestDto requestDto = new ReceiveItemRequestDto("4", "003871234567890122", "1-2-4");

        when(warehouseRepository.findByWarehouseNumber("4")).thenReturn(Optional.of(warehouse));
        when(ssccRepository.findBySscc("003871234567890122")).thenReturn(Optional.of(sscc));

        receiveItemService.handleReceive(requestDto);

        ArgumentCaptor<ReceiveItemEvent> eventCaptor = ArgumentCaptor.forClass(ReceiveItemEvent.class);
        verify(kafkaTemplate, times(1)).send(eq("stock-movement"), eventCaptor.capture());

        ReceiveItemEvent publishedEvent = eventCaptor.getValue();
        Assertions.assertEquals("003871234567890122", publishedEvent.getSscc());
        Assertions.assertEquals("1-2-4", publishedEvent.getLocationCode());
    }

    @Test
    @DisplayName("Should throw ConflictException if SSCC already received")
    public void should_throw_if_sscc_already_received() {
        ReceiveItemRequestDto requestDto = new ReceiveItemRequestDto("4", "003871234567890122", "1-2-4");
        sscc.setReceivedTimestamp(Instant.now()); // Already received

        when(warehouseRepository.findByWarehouseNumber("4")).thenReturn(Optional.of(warehouse));
        when(ssccRepository.findBySscc("003871234567890122")).thenReturn(Optional.of(sscc));


        Assertions.assertThrows(ConflictException.class, () -> receiveItemService.handleReceive(requestDto));

        verify(kafkaTemplate, never()).send(any(), any());
    }
}