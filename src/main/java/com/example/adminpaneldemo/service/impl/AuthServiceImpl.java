package com.example.adminpaneldemo.service.impl;

import com.example.adminpaneldemo.dto.request.LoginRequestDto;
import com.example.adminpaneldemo.dto.request.RegisterRequestDto;
import com.example.adminpaneldemo.dto.response.UserResponseDto;
import com.example.adminpaneldemo.entity.Authority;
import com.example.adminpaneldemo.entity.Users;
import com.example.adminpaneldemo.enums.Role;
import com.example.adminpaneldemo.exception.AlreadyExistException;
import com.example.adminpaneldemo.exception.NotFoundException;
import com.example.adminpaneldemo.mapper.UserMapper;
import com.example.adminpaneldemo.repository.AuthorityRepository;
import com.example.adminpaneldemo.repository.UsersRepository;
import com.example.adminpaneldemo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsersRepository usersRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(RegisterRequestDto registerRequestDto) {
        log.info("User register method started");
        if (usersRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new AlreadyExistException("This user already exist");
        } else {
            Authority authority = (Authority) authorityRepository.findByName(Role.USER.name())
                    .orElseThrow(() -> new NotFoundException("Authority not found"));
            log.info("Authority found: {}", authority);
            registerRequestDto.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
            Users user = userMapper.toEntity(registerRequestDto);
            user.setAuthority(authority);
            Users savedUser = usersRepository.save(user);
            UserResponseDto userDTO = userMapper.toDto(savedUser);
            log.info("user registered as a user role, gmail: {}", user.getEmail());
            return userDTO;
        }
    }

    @Override
    public Optional<Users> authenticate(LoginRequestDto request) {
        return usersRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()));

    }
}
