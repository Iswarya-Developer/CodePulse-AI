package com.codepulse.auth.service;

import com.codepulse.auth.dto.request.LoginRequest;
import com.codepulse.auth.dto.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

}
