package com.codepulse.auth.mapper;

import com.codepulse.auth.dto.request.RegisterRequest;
import com.codepulse.auth.entity.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(RegisterRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .active(true)
                .build();

    }

}