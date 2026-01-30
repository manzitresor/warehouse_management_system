package com.thegym.warehousemanagementsystem.producers;


import com.thegym.warehousemanagementsystem.KafkaEvents.ReceiveItemEvent;
import com.thegym.warehousemanagementsystem.dtos.requestDto.ReceiveItemRequestDto;
import com.thegym.warehousemanagementsystem.enums.EventType;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@AllArgsConstructor
public class ReceiveItemProducer {
    private WarehouseRepository warehouseRepository;

    private KafkaTemplate<String, ReceiveItemEvent> kafkaTemplate;


    public void initialCheck(ReceiveItemRequestDto requestDto){
        var warehouse = warehouseRepository.findByWarehouseNumber(requestDto.getWarehouseNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        if (Boolean.FALSE.equals(warehouse.getActive())) {
            throw new ConflictException("Warehouse " + warehouse.getWarehouseNumber() + " is inactive.");
        }

        ReceiveItemEvent event = new ReceiveItemEvent(
                EventType.RECEIVE,
                requestDto.getWarehouseNumber(),
                requestDto.getLocationCode(),
                requestDto.getSscc(),
                Instant.now().toEpochMilli()
        );

        kafkaTemplate.send("stock-movement", event);
    }
}
