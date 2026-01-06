package com.thegym.warehousemanagementsystem.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WarehouseRequestDto {
    @NotBlank(message = "WarehouseNumber is required")
    private String warehouseNumber;

    @NotBlank(message = "Warehouse name is required")
    private String name;
}
