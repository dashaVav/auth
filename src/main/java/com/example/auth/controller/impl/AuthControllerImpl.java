package com.example.auth.controller.impl;

import com.example.auth.controller.AuthController;
import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.TokenValidationDTO;
import com.example.auth.dto.UserAuthDTO;
import com.example.auth.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthenticationServiceImpl authenticationService;

    @Override
    public ResponseEntity<JwtAuthDTO> registration(UserAuthDTO request) {
        log.info("auth/registration requested by user - {}", request.getUsername());
        return ResponseEntity.ok(authenticationService.registration(request));
    }

    @Override
    public ResponseEntity<JwtAuthDTO> login(UserAuthDTO request) {
        log.info("auth/login requested by user - {}", request.getUsername());
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @Override
    public ResponseEntity<JwtAuthDTO> isTokenValid(TokenValidationDTO request) {
        log.info("auth/check requested with token - {}...", request.getToken().substring(0, 10));
        return ResponseEntity.ok(authenticationService.validateToken(request.getToken()));
    }

    @Override
    public ResponseEntity<JwtAuthDTO> getAdmin(TokenValidationDTO request) {
        log.info("auth/get/admin requested with token - {}...", request.getToken().substring(0, 10));
        return ResponseEntity.ok(authenticationService.getAdmin(request.getToken()));
    }
}
