package com.example.auth.controller.impl;

import com.example.auth.controller.AuthController;
import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.TokenValidationDTO;
import com.example.auth.dto.UserAuthDTO;
import com.example.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authenticationService;

    public ResponseEntity<JwtAuthDTO> registration(UserAuthDTO request) {
        return ResponseEntity.ok(authenticationService.registration(request));
    }

    public ResponseEntity<JwtAuthDTO> login(UserAuthDTO request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    public ResponseEntity<JwtAuthDTO> isTokenValid(TokenValidationDTO request) {
        return ResponseEntity.ok(authenticationService.validateToken(request.getToken()));
    }
}
