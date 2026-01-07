package com.thegym.warehousemanagementsystem.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCartonHeaderDto {
    @NotBlank(message = "Description is required")
    private String description;
}
