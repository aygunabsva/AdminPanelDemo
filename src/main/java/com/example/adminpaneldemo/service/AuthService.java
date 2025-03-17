package com.example.adminpaneldemo.service;

import com.example.adminpaneldemo.dto.request.LoginRequestDto;
import com.example.adminpaneldemo.dto.request.RegisterRequestDto;
import com.example.adminpaneldemo.dto.response.UserResponseDto;
import com.example.adminpaneldemo.entity.Users;

import java.util.Optional;

public interface AuthService {
    UserResponseDto register(RegisterRequestDto registerRequestDto);

    Optional<Users> authenticate(LoginRequestDto request);
}
