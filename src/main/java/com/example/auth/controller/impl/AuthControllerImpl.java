package com.example.auth.controller.impl;

import com.example.auth.controller.AuthController;
import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.UserAuthDTO;
import com.example.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authenticationService;

    public JwtAuthDTO signUp(UserAuthDTO request) {
        return authenticationService.signUp(request);
    }

    public JwtAuthDTO signIn(UserAuthDTO request) {
        return authenticationService.signIn(request);
    }
}
