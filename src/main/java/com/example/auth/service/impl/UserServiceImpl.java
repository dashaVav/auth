package com.example.auth.service.impl;

import com.example.auth.exception.UserAlreadyExistsException;
import com.example.auth.model.Role;
import com.example.auth.model.User;
import com.example.auth.repository.UsersRepository;
import com.example.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository repository;

    private User save(User user) {
        return repository.save(user);
    }

    @Override
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exist");
        }
        return save(user);
    }

    private User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    public UserDetails getAdmin(String username) {
        User user = getByUsername(username);
        user.setRole(Role.ROLE_ADMIN);
        return repository.save(user);
    }
}
