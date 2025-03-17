package com.example.adminpaneldemo.dto.response;

import com.example.adminpaneldemo.enums.Role;
import lombok.Data;

@Data
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String workplace;
    private String photoUrl;
    private Role role;
}
