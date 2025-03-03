package com.example.adminpaneldemo.configuration;

import com.example.adminpaneldemo.entity.Authority;
import com.example.adminpaneldemo.entity.Users;
import com.example.adminpaneldemo.exception.NotFoundException;
import com.example.adminpaneldemo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Configuration
public class CustomUserDetailService implements UserDetailsService {
    private final UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));
        Authority authority = user.getAuthority();
        List<String> roles = List.of(authority.getName());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(roles))
                .build();
    }
}
