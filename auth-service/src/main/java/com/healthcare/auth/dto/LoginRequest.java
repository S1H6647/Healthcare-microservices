package com.healthcare.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Identifier (Email or Phone) is required")
        String identifier,

        @NotBlank(message = "Password is required")
        String password
) {
}
