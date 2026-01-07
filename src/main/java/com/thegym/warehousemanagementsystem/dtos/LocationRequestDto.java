package com.thegym.warehousemanagementsystem.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationRequestDto {
    @NotNull(message = "row is required")
    private Integer row;

    @NotNull(message = "section is required")
    private Integer section;

    @NotNull(message = "shelf is required")
    private Integer shelf;
}
