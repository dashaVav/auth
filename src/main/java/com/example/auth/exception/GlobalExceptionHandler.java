package com.example.auth.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<AuthError> handleTheException(RuntimeException e, HttpStatus status) {
        log.error("Exception: {} handled normally. Message: {}", e.getClass().getName(), e.getMessage());
        return new ResponseEntity<>(
                new AuthError(e.getMessage(), status.value()),
                status
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<AuthError> handleBadRequestsException(RuntimeException e) {
        return handleTheException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExpiredJwtException.class, InvalidTokenException.class, InvalidPasswordException.class})
    public ResponseEntity<AuthError> handleForbiddenException(RuntimeException e) {
        return handleTheException(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AuthError> handleNotFoundException(RuntimeException e) {
        return handleTheException(e, HttpStatus.NOT_FOUND);
    }
}
