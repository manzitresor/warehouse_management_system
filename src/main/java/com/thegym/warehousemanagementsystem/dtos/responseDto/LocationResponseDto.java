package com.thegym.warehousemanagementsystem.dtos.responseDto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationResponseDto {
    private Integer row;
    private Integer column;
    private Integer shelf;
    private String locationCode;
}
