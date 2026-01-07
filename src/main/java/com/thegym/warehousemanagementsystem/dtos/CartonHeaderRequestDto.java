package com.thegym.warehousemanagementsystem.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartonHeaderRequestDto {

    @NotBlank(message = "barcode is required")
    private String barcode;

    @NotBlank(message = "description is required")
    private String description;
}
