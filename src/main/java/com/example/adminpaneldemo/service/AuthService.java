package com.example.adminpaneldemo.service;

import com.example.adminpaneldemo.dto.request.UsersRegisterDTO;
import com.example.adminpaneldemo.dto.response.UsersDTO;

public interface AuthService {
    UsersDTO register(UsersRegisterDTO usersRegisterDTO);
}
