package com.healthcare.pharmacist.service;

import com.healthcare.pharmacist.dto.PharmacistRequest;
import com.healthcare.pharmacist.dto.PharmacistResponse;
import com.healthcare.pharmacist.entity.Pharmacist;
import com.healthcare.pharmacist.exception.DuplicateResourceException;
import com.healthcare.pharmacist.exception.ResourceNotFoundException;
import com.healthcare.pharmacist.repository.PharmacistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacistService {

    private final PharmacistRepository pharmacistRepository;

    @Transactional
    public PharmacistResponse registerPharmacist(PharmacistRequest request) {
        if (pharmacistRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "Pharmacist with email " + request.email() + " already exists"
            );
        }
        if (pharmacistRepository.existsByLicenseNumber(request.licenseNumber())) {
            throw new DuplicateResourceException(
                    "License number " + request.licenseNumber() + " is already registered"
            );
        }

        Pharmacist pharmacist = Pharmacist.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .licenseNumber(request.licenseNumber())
                .build();

        return PharmacistResponse.from(pharmacistRepository.save(pharmacist));
    }

    @Transactional(readOnly = true)
    public PharmacistResponse getPharmacistById(Long id) {
        return pharmacistRepository.findById(id)
                .map(PharmacistResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pharmacist not found with id: " + id
                ));
    }

    @Transactional(readOnly = true)
    public List<PharmacistResponse> getAllPharmacists() {
        return pharmacistRepository.findAll()
                .stream()
                .map(PharmacistResponse::from)
                .toList();
    }

    @Transactional
    public PharmacistResponse updatePharmacist(Long id, PharmacistRequest request) {
        Pharmacist pharmacist = pharmacistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pharmacist not found with id: " + id
                ));

        if (!pharmacist.getEmail().equals(request.email())
                && pharmacistRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "Email " + request.email() + " is already in use"
            );
        }

        if (!pharmacist.getLicenseNumber().equals(request.licenseNumber())
                && pharmacistRepository.existsByLicenseNumber(request.licenseNumber())) {
            throw new DuplicateResourceException(
                    "License number " + request.licenseNumber() + " is already registered"
            );
        }

        pharmacist.setFirstName(request.firstName());
        pharmacist.setLastName(request.lastName());
        pharmacist.setEmail(request.email());
        pharmacist.setPhone(request.phone());
        pharmacist.setLicenseNumber(request.licenseNumber());

        return PharmacistResponse.from(pharmacistRepository.save(pharmacist));
    }

    @Transactional
    public void deletePharmacist(Long id) {
        if (!pharmacistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pharmacist not found with id: " + id);
        }
        pharmacistRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PharmacistResponse getPharmacistByEmail(String email) {
        Pharmacist pharmacist = pharmacistRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacist not found with email: " + email));

        return PharmacistResponse.from(pharmacist);
    }
}
