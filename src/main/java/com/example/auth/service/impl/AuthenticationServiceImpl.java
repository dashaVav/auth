package com.example.auth.service.impl;

import com.example.auth.dto.JwtAuthDTO;
import com.example.auth.dto.UserAuthDTO;
import com.example.auth.exception.InvalidPasswordException;
import com.example.auth.exception.InvalidTokenException;
import com.example.auth.model.Role;
import com.example.auth.model.User;
import com.example.auth.service.AuthenticationService;
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
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserServiceImpl userService;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthDTO registration(UserAuthDTO request) {
        User user = userService.create(
                new User()
                        .setUsername(request.getUsername())
                        .setPassword(passwordEncoder.encode(request.getPassword()))
                        .setRole(Role.ROLE_USER)
        );

        String token = jwtService.generateToken(user);
        return new JwtAuthDTO(user.getUsername(), token, user.getRole());
    }

    @Override
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

        String token = jwtService.generateToken(user);
        return new JwtAuthDTO(user.getUsername(), token, getRoleFromAuthorities(user.getAuthorities()));
    }

    @Override
    public JwtAuthDTO getAdmin(String token) {
        var username = jwtService.extractUserName(token);
        if (StringUtils.isNotEmpty(username)) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails)) {
                userDetails = userService.getAdmin(username);
                token = jwtService.generateToken(userDetails);
                return new JwtAuthDTO(
                        userDetails.getUsername(),
                        token,
                        getRoleFromAuthorities(userDetails.getAuthorities())
                );
            }
        }
        throw new InvalidTokenException("Invalid token");
    }

    @Override
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