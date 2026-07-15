package com.co.istad.piseth.spring_web_mvc.features.auth;

import com.co.istad.piseth.spring_web_mvc.features.auth.dto.RegisterRequest;
import com.co.istad.piseth.spring_web_mvc.features.auth.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
}
