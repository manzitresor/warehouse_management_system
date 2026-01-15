package com.thegym.warehousemanagementsystem.dtos.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private String name;
    private String email;
}
