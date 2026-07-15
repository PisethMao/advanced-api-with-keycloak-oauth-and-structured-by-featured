package com.co.istad.piseth.spring_web_mvc.features.auth;

import com.co.istad.piseth.spring_web_mvc.features.auth.dto.RegisterRequest;
import com.co.istad.piseth.spring_web_mvc.features.auth.dto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
}
