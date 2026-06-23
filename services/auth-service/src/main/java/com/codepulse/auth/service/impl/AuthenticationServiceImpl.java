package com.codepulse.auth.service.impl;

import com.codepulse.auth.dto.request.LoginRequest;
import com.codepulse.auth.dto.response.LoginResponse;
import com.codepulse.auth.entity.User;
import com.codepulse.auth.repository.UserRepository;
import com.codepulse.auth.security.JwtService;
import com.codepulse.auth.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());

// We'll add roles later
        claims.put("role", "USER");

        String token = jwtService.generateToken(claims, user.getEmail());

        return LoginResponse.builder()
                .accessToken(token)
                .expiresIn(expiration / 1000)
                .build();

    }

}