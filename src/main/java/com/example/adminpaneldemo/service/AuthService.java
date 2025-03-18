package com.example.adminpaneldemo.service;

import com.example.adminpaneldemo.dto.request.LoginRequestDto;
import com.example.adminpaneldemo.dto.request.RegisterRequestDto;
import com.example.adminpaneldemo.dto.request.VerifyEmailDto;
import com.example.adminpaneldemo.dto.response.UserResponseDto;
import com.example.adminpaneldemo.entity.Users;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface AuthService {
    UserResponseDto register(RegisterRequestDto registerRequestDto);

    ResponseEntity<?> authenticate(LoginRequestDto request);

    boolean verifyEmail(VerifyEmailDto verifyEmailDto);
}
