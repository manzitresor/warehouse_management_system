package com.thegym.warehousemanagementsystem.KafkaEvents;

import com.thegym.warehousemanagementsystem.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveItemEvent {
    private EventType eventType;
    private String warehouseNumber;
    private String locationCode;
    private String sscc;
    private Long timestamp;
}
