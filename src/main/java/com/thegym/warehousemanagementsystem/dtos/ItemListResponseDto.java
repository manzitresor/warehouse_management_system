package com.thegym.warehousemanagementsystem.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemListResponseDto {
    private String itemNumber;
    private String locationCode;
    private Integer quantity;
}
