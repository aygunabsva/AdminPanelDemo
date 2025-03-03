package com.example.adminpaneldemo.configuration;

import com.example.adminpaneldemo.filter.JwtAuthorizationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(SecUrls.whiteList).permitAll()
                                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/**").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.TRACE, "/**").permitAll()
                                .requestMatchers(HttpMethod.HEAD, "/**").permitAll()
                                .anyRequest().authenticated()
                ).exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN)
                        )
                );
        return http.build();
    }
}
