package com.example.adminpaneldemo.dto.request;

import lombok.Data;

@Data
public class VerifyEmailDto {
    private String email;
    private String token;
}
