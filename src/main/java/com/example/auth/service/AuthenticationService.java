package com.example.auth.service;

import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.UserAuthDTO;
import com.example.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public JwtAuthDTO signUp(UserAuthDTO request) {

        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthDTO(jwt);
    }

    public JwtAuthDTO signIn(UserAuthDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthDTO(jwt);
    }
}