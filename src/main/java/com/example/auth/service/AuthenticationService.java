package com.example.auth.service;

import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.UserAuthDTO;
import com.example.auth.exception.InvalidTokenException;
import com.example.auth.model.Role;
import com.example.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthDTO registration(UserAuthDTO request) {
        User user = userService.create(
                new User()
                        .setUsername(request.getUsername())
                        .setPassword(passwordEncoder.encode(request.getPassword()))
                        .setRole(Role.ROLE_USER)
        );

        String jwt = jwtService.generateToken(user);
        return new JwtAuthDTO(user.getUsername(), jwt, user.getRole());
    }

    public JwtAuthDTO login(UserAuthDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        String jwt = jwtService.generateToken(user);
        return new JwtAuthDTO(user.getUsername(), jwt, user.getAuthorities().stream().toList().getFirst());
    }

    public JwtAuthDTO validateToken(String token) {
        var username = jwtService.extractUserName(token);

        if (StringUtils.isNotEmpty(username)) {
            UserDetails userDetails = userService
                    .userDetailsService()
                    .loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                return new JwtAuthDTO(
                        userDetails.getUsername(),
                        token,
                        userDetails.getAuthorities().stream().toList().getFirst()
                );
            }
        }
        throw new InvalidTokenException("Invalid token");
    }
}