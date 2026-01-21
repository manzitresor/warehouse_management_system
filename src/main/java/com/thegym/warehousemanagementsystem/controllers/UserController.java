package com.thegym.warehousemanagementsystem.controllers;

import com.thegym.warehousemanagementsystem.dtos.requestDto.UserRequestDto;
import com.thegym.warehousemanagementsystem.dtos.responseDto.UserResponseDto;
import com.thegym.warehousemanagementsystem.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createItem(@RequestBody UserRequestDto userRequestDto) {
        var user = userService.create(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
