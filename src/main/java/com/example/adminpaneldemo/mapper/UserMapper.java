package com.example.adminpaneldemo.mapper;

import com.example.adminpaneldemo.dto.request.UsersRegisterDTO;
import com.example.adminpaneldemo.dto.response.UsersDTO;
import com.example.adminpaneldemo.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract Users toEntity(UsersRegisterDTO usersRegisterDTO);

    @Mapping(source = "id", target = "userId")
    public abstract UsersDTO toDto(Users user);
}
