package com.example.auth.dto;

import com.example.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthDTO {
    private String username;
    private String token;
    private Role role;

    public JwtAuthDTO(String username, String token, GrantedAuthority role) {
        this.username = username;
        this.token = token;
        this.role = Role.valueOf(role.getAuthority());
    }
}