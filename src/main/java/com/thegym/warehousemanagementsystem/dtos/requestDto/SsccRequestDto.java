package com.thegym.warehousemanagementsystem.dtos.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SsccRequestDto {
    @NotBlank(message = "sscc is required")
    private String sscc;
}
