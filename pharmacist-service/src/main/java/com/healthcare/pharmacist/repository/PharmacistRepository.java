package com.healthcare.pharmacist.repository;

import com.healthcare.pharmacist.entity.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
    boolean existsByEmail(String email);

    boolean existsByLicenseNumber(String licenseNumber);

    Optional<Pharmacist> findByEmail(String email);
}
