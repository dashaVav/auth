package com.example.auth.service;

import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.UserAuthDTO;

public interface AuthenticationService {
    JwtAuthDTO registration(UserAuthDTO request);

    JwtAuthDTO login(UserAuthDTO request);

    JwtAuthDTO getAdmin(String token);

    JwtAuthDTO validateToken(String token);
}