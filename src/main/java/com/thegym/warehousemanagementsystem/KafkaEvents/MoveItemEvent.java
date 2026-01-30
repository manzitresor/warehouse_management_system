package com.thegym.warehousemanagementsystem.KafkaEvents;


import com.thegym.warehousemanagementsystem.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoveItemEvent {
    private EventType eventType;
    private String warehouseNumber;
    private String itemNumber;
    private String fromLocation;
    private String toLocation;
    private Integer quantity;
    private Long timestamp;
}
