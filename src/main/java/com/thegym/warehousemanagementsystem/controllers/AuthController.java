package com.thegym.warehousemanagementsystem.controllers;


import com.thegym.warehousemanagementsystem.dtos.requestDto.LoginRequestDto;
import com.thegym.warehousemanagementsystem.dtos.responseDto.JwtResponseDto;
import com.thegym.warehousemanagementsystem.repositories.UserRepository;
import com.thegym.warehousemanagementsystem.services.AuthService;
import com.thegym.warehousemanagementsystem.services.JwtService;
import com.thegym.warehousemanagementsystem.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(), requestDto.getPassword())
        );
        var user = userService.getUserByEmail(requestDto.getEmail());
        var token = jwtService.generateToken(user);
        return ResponseEntity.ok().body(new JwtResponseDto(token));
    }

}
