package com.example.adminpaneldemo.controller;

import com.example.adminpaneldemo.dto.request.UsersRegisterDTO;
import com.example.adminpaneldemo.dto.response.UsersDTO;
import com.example.adminpaneldemo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public UsersDTO register(@RequestBody @Valid UsersRegisterDTO customerRegisterDTO) {
        return authService.register(customerRegisterDTO);
    }
}
