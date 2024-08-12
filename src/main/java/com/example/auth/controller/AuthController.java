package com.example.auth.controller;

import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.UserAuthDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthController {
    @PostMapping("/sign-up")
    JwtAuthDTO signUp(@Valid @RequestBody UserAuthDTO request);

    @PostMapping("/sign-in")
    JwtAuthDTO signIn(@Valid @RequestBody UserAuthDTO request);
}
