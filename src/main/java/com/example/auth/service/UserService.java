package com.example.auth.service;

import com.example.auth.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User create(User user);

    UserDetailsService userDetailsService();

    UserDetails getAdmin(String username);
}
