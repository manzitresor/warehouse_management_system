package com.thegym.warehousemanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiveItemRequestDto {
    private String warehouseNumber;
    private String sscc;
    private String locationCode;
}
