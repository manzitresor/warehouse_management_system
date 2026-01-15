package com.thegym.warehousemanagementsystem.services;


import com.thegym.warehousemanagementsystem.exceptions.ResourceNotFoundException;
import com.thegym.warehousemanagementsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws ResourceNotFoundException {
        var user = userRepository.findUsersByEmail(email).orElseThrow(
                () -> new   ResourceNotFoundException("User not found"));
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

}
