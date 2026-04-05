package com.healthcare.auth.controller;

import com.healthcare.auth.dto.*;
import com.healthcare.auth.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration and login")
public class AuthController {

    private final UserService userService;

    /**
     * Register a new patient account (public).
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    /**
     * Admin creates a receptionist account.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/receptionist/register")
    public ResponseEntity<RegisterResponse> registerReceptionist(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerReceptionist(request));
    }

    /**
     * Admin creates a doctor account.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/doctor/register")
    public ResponseEntity<RegisterResponse> registerDoctor(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerDoctor(request));
    }

    /**
     * Authenticate and receive a JWT bearer token.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestHeader("X-User-Email") String email,
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(email, request);
        return ResponseEntity.noContent().build();
    }
}
