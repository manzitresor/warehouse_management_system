package com.thegym.warehousemanagementsystem.services;

import com.thegym.warehousemanagementsystem.dtos.requestDto.UserRequestDto;
import com.thegym.warehousemanagementsystem.dtos.responseDto.UserResponseDto;
import com.thegym.warehousemanagementsystem.entities.User;
import com.thegym.warehousemanagementsystem.exceptions.ConflictException;
import com.thegym.warehousemanagementsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDto create(UserRequestDto requestDto) {
        if (userRepository.existsUserByEmail(requestDto.getEmail())) {
            throw new ConflictException("User with the same email already exists");
        }
        User user = new User();
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        return new UserResponseDto(
                user.getName(),
                user.getEmail()
        );
    }
}
