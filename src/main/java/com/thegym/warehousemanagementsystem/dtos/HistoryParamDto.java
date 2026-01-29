package com.thegym.warehousemanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryParamDto {
    private String warehouseNumber;
    private String itemNumber;
    private String locationCode;
}
