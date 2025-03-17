package com.example.adminpaneldemo.controller;

import com.example.adminpaneldemo.dto.request.LoginRequestDto;
import com.example.adminpaneldemo.dto.request.RegisterRequestDto;
import com.example.adminpaneldemo.dto.response.UserResponseDto;
import com.example.adminpaneldemo.entity.Users;
import com.example.adminpaneldemo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@RequestBody @Valid RegisterRequestDto customerRegisterDTO) {
        return authService.register(customerRegisterDTO);
    }

    @ResponseBody
    @PostMapping("/log-in")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginReq) {
        return authService.authenticate(loginReq);
    }


}
