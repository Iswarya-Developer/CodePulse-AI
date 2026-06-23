package com.codepulse.auth.controller;

import com.codepulse.auth.dto.common.ApiResponse;
import com.codepulse.auth.dto.request.LoginRequest;
import com.codepulse.auth.dto.request.RegisterRequest;
import com.codepulse.auth.dto.response.LoginResponse;
import com.codepulse.auth.dto.response.RegisterResponse;
import com.codepulse.auth.service.AuthenticationService;
import com.codepulse.auth.service.UserService;
import com.codepulse.auth.util.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = userService.register(request);

        ApiResponse<RegisterResponse> apiResponse =
                ApiResponse.<RegisterResponse>builder()
                        .success(true)
                        .message(AppConstants.USER_REGISTERED)
                        .data(response)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authenticationService.login(request);

        return ResponseEntity.ok(

                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Login Successful")
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/me")
    public ResponseEntity<String> me() {

        return ResponseEntity.ok("Authenticated Successfully");

    }
}