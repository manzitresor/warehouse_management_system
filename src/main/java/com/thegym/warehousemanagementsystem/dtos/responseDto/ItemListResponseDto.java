package com.thegym.warehousemanagementsystem.dtos.responseDto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemListResponseDto {
    private String itemNumber;
    private String locationCode;
    private Integer quantity;
}
