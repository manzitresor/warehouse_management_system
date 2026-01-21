package com.thegym.warehousemanagementsystem.dtos.requestDto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String email;
    private String password;
}
