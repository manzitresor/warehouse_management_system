package com.thegym.warehousemanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateWarehouseRequestDto {
    private String name;
    private Boolean active;
}
