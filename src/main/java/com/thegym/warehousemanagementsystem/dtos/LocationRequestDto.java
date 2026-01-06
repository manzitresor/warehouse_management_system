package com.thegym.warehousemanagementsystem.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationRequestDto {
    @NotNull
    private Integer row;

    @NotNull
    private Integer section;

    @NotNull
    private Integer shelf;
}
