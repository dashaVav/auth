package com.example.auth.controller;

import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.TokenValidationDTO;
import com.example.auth.dto.UserAuthDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthController {
    @PostMapping("/registration")
    ResponseEntity<JwtAuthDTO> registration(@Valid @RequestBody UserAuthDTO request);

    @PostMapping("/login")
    ResponseEntity<JwtAuthDTO> login(@Valid @RequestBody UserAuthDTO request);

    @PostMapping("/check")
    ResponseEntity<JwtAuthDTO> isTokenValid(@Valid @RequestBody TokenValidationDTO request);
}
