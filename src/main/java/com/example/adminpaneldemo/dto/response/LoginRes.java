package com.example.adminpaneldemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRes {
    private String email;
    private String token;
}
