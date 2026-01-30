package com.thegym.warehousemanagementsystem.producers;


import com.thegym.warehousemanagementsystem.KafkaEvents.MoveItemEvent;
import com.thegym.warehousemanagementsystem.dtos.requestDto.MoveItemRequestDto;
import com.thegym.warehousemanagementsystem.enums.EventType;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class MoveItemProducer {
    private final WarehouseRepository warehouseRepository;
    private final KafkaTemplate<String, MoveItemEvent> kafkaTemplate;

    public void initiateMove(MoveItemRequestDto requestDto) {
        warehouseRepository.findByWarehouseNumber(requestDto.getWarehouseNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse Not Found"));

        MoveItemEvent event = new MoveItemEvent(
                EventType.MOVE,
                requestDto.getWarehouseNumber(),
                requestDto.getItemNumber(),
                requestDto.getFromLocationCode(),
                requestDto.getToLocationCode(),
                requestDto.getQuantity(),
                Instant.now().toEpochMilli()
        );

        kafkaTemplate.send("stock-movement", event);
    }
}
