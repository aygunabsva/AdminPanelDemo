package com.example.adminpaneldemo.dto.request;

import lombok.Data;

@Data
public class UsersRegisterDTO {
    private String name;
    private String surName;
    private String email;
    private String password;
}
