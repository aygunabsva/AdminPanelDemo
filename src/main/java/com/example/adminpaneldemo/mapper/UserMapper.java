package com.example.adminpaneldemo.mapper;

import com.example.adminpaneldemo.dto.request.RegisterRequestDto;
import com.example.adminpaneldemo.dto.response.UserResponseDto;
import com.example.adminpaneldemo.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract Users toEntity(RegisterRequestDto registerRequestDto);

    @Mapping(source = "authority.name", target = "role")  // Map authority.name to authorityName in DTO
    public abstract UserResponseDto toDto(Users user);
}
