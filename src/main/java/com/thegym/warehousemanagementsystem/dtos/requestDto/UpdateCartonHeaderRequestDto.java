package com.thegym.warehousemanagementsystem.dtos.requestDto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCartonHeaderRequestDto {
    @NotBlank(message = "Description is required")
    private String description;
}
