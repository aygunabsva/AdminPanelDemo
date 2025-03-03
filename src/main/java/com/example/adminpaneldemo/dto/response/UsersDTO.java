package com.example.adminpaneldemo.dto.response;

import lombok.Data;

@Data
public class UsersDTO {
    private Long userId;
    private String name;
    private String surName;
    private String email;
    private String password;
}
