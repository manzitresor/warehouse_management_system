package com.thegym.warehousemanagementsystem.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartonHeaderResponseDto {
    private String barcode;
    private String Description;
}
