package com.example.auth.dto;

import com.example.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthDTO {
    private String username;
    private String token;
    private Role role;
}