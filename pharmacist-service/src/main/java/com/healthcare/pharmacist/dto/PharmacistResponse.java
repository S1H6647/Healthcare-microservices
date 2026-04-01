package com.healthcare.pharmacist.dto;

import com.healthcare.pharmacist.entity.Pharmacist;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PharmacistResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String licenseNumber,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PharmacistResponse from(Pharmacist pharmacist) {
        return PharmacistResponse.builder()
                .id(pharmacist.getId())
                .firstName(pharmacist.getFirstName())
                .lastName(pharmacist.getLastName())
                .email(pharmacist.getEmail())
                .phone(pharmacist.getPhone())
                .licenseNumber(pharmacist.getLicenseNumber())
                .createdAt(pharmacist.getCreatedAt())
                .updatedAt(pharmacist.getUpdatedAt())
                .build();
    }
}
