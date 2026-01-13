package com.thegym.warehousemanagementsystem.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemResponseDto {
    private String itemNumber;
    private String locationCode;
    private String cartonBarcode;
    private String cartonDescription;
    private int quantity;
}
