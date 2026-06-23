package com.codepulse.auth.service;

import com.codepulse.auth.dto.response.RegisterResponse;
import com.codepulse.auth.dto.request.RegisterRequest;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

}