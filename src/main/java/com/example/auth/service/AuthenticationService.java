package com.example.auth.service;

import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.UserAuthDTO;
import com.example.auth.exception.InvalidPasswordException;
import com.example.auth.exception.InvalidTokenException;
import com.example.auth.model.Role;
import com.example.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            authenticationManager.authenticate(auth);
        } catch (Exception e) {
            throw new InvalidPasswordException("Invalid password");
        }

        String jwt = jwtService.generateToken(user);
        return new JwtAuthDTO(user.getUsername(), jwt, getRoleFromAuthorities(user.getAuthorities()));
    }

    public JwtAuthDTO validateToken(String token) {
        var username = jwtService.extractUserName(token);

        if (StringUtils.isNotEmpty(username)) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails)) {
                return new JwtAuthDTO(
                        userDetails.getUsername(),
                        token,
                        getRoleFromAuthorities(userDetails.getAuthorities())
                );
            }
        }
        throw new InvalidTokenException("Invalid token");
    }

    private Role getRoleFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        var role = authorities.stream().toList().getFirst();
        return Role.valueOf(role.getAuthority());
    }
}