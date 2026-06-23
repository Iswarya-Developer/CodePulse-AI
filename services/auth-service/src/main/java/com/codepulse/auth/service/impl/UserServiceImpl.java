package com.codepulse.auth.service.impl;

import com.codepulse.auth.dto.request.RegisterRequest;
import com.codepulse.auth.dto.response.RegisterResponse;
import com.codepulse.auth.entity.User;
import com.codepulse.auth.exception.EmailAlreadyExistsException;
import com.codepulse.auth.repository.UserRepository;
import com.codepulse.auth.service.UserService;
import com.codepulse.auth.util.AppConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    AppConstants.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .build();

        User savedUser = repository.save(user);

        return RegisterResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .message(AppConstants.USER_REGISTERED)
                .build();

    }

}