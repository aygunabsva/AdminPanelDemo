package com.example.adminpaneldemo.dto.request;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String workplace;
    private boolean termsAccepted;
}
