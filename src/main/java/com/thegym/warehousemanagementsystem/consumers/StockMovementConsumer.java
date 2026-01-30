package com.thegym.warehousemanagementsystem.consumers;

import com.thegym.warehousemanagementsystem.KafkaEvents.MoveItemEvent;
import com.thegym.warehousemanagementsystem.KafkaEvents.ReceiveItemEvent;
import com.thegym.warehousemanagementsystem.dtos.requestDto.MoveItemRequestDto;
import com.thegym.warehousemanagementsystem.dtos.requestDto.ReceiveItemRequestDto;
import com.thegym.warehousemanagementsystem.enums.EventType;
import com.thegym.warehousemanagementsystem.services.MoveItemService;
import com.thegym.warehousemanagementsystem.services.ReceiveItemService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@KafkaListener(topics = "stock-movement", groupId = "inventory-group")
public class StockMovementConsumer {
    private ReceiveItemService receiveItemService;
    private MoveItemService moveItemService;

    @Async
    @Transactional
    @KafkaHandler
    public void receiveItem(ReceiveItemEvent event) {
        try {
            if (EventType.RECEIVE.equals(event.getEventType())) {
                ReceiveItemRequestDto itemDto = new ReceiveItemRequestDto(
                        event.getWarehouseNumber(),
                        event.getSscc(),
                        event.getLocationCode()
                );

                receiveItemService.handleReceive(itemDto);
                log.info("Item Received Successfully");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    @Transactional
    @KafkaHandler
    public void handleMove(MoveItemEvent event) {
        log.info("Processing Move Intent for Item: {}", event.getItemNumber());
        try {
            MoveItemRequestDto dto = new MoveItemRequestDto(
                    event.getWarehouseNumber(),
                    event.getItemNumber(),
                    event.getFromLocation(),
                    event.getToLocation(),
                    event.getQuantity()
            );

            moveItemService.moveItem(dto);
            log.info("Item Moved Successfully: {}", event.getItemNumber());
        } catch (Exception e) {
            log.error("Failed to process move for Item {}: {}", event.getItemNumber(), e.getMessage());
        }
    }


}
