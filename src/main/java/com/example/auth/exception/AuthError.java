package com.example.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthError {
    private String error;
    private Integer status;
}
