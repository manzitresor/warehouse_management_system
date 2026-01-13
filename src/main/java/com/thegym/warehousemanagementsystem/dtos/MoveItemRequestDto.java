package com.thegym.warehousemanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveItemRequestDto {
    private String warehouseNumber;
    private String itemNumber;
    private String fromLocationCode;
    private String toLocationCode;
    private Integer quantity;
}
