package com.example.adminpaneldemo.service.impl;

import com.example.adminpaneldemo.dto.request.UsersRegisterDTO;
import com.example.adminpaneldemo.dto.response.UsersDTO;
import com.example.adminpaneldemo.entity.Authority;
import com.example.adminpaneldemo.entity.Users;
import com.example.adminpaneldemo.enums.Roles;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsersRepository usersRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UsersDTO register(UsersRegisterDTO usersRegisterDTO) {
        log.info("User register method started");
        if (usersRepository.findByEmail(usersRegisterDTO.getEmail()).isPresent()) {
            throw new AlreadyExistException("This user already exist");
        } else {
            Authority authority = (Authority) authorityRepository.findByName(Roles.USER.name())
                    .orElseThrow(() -> new NotFoundException("Authority not found"));

            usersRegisterDTO.setPassword(passwordEncoder.encode(usersRegisterDTO.getPassword()));
            Users user = userMapper.toEntity(usersRegisterDTO);
            user.setAuthority(authority);
            Users savedUser = usersRepository.save(user);
            UsersDTO userDTO = userMapper.toDto(savedUser);
            log.info("user registered as a customer role, username: {}", user.getEmail());
            return userDTO;
        }
    }
}
