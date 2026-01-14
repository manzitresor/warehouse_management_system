package com.thegym.warehousemanagementsystem.dtos.responseDto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemResponseDto {
    private String itemNumber;
    private String locationCode;
    private String cartonBarcode;
    private String cartonDescription;
    private Integer quantity;
}
