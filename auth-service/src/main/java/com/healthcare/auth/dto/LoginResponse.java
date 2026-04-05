package com.healthcare.auth.dto;

import com.healthcare.auth.entity.User;
import lombok.Builder;

@Builder
public record LoginResponse(
        String token,
        String email,
        String phone,
        String role
) {
    public static LoginResponse from(User user, String token) {
        return new LoginResponse(
                token,
                user.getEmail(),
                user.getPhone(),
                user.getRole().toString()
        );
    }
}