package com.healthcare.pharmacist.controller;

import com.healthcare.pharmacist.dto.PharmacistRequest;
import com.healthcare.pharmacist.dto.PharmacistResponse;
import com.healthcare.pharmacist.service.PharmacistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacists")
@RequiredArgsConstructor
@Tag(name = "Pharmacists", description = "Pharmacist management")
public class PharmacistController {

    private final PharmacistService pharmacistService;

    @PostMapping("/register")
    public ResponseEntity<PharmacistResponse> registerPharmacist(
            @Valid @RequestBody PharmacistRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pharmacistService.registerPharmacist(request));
    }

    @GetMapping("/me")
    public ResponseEntity<PharmacistResponse> getMyProfile(
            @RequestHeader(value = "X-User-Email")
            String email
    ) {
        return ResponseEntity.ok(pharmacistService.getPharmacistByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PharmacistResponse> getPharmacist(@PathVariable Long id) {
        return ResponseEntity.ok(pharmacistService.getPharmacistById(id));
    }

    @GetMapping
    public ResponseEntity<List<PharmacistResponse>> getAllPharmacists() {
        return ResponseEntity.ok(pharmacistService.getAllPharmacists());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PharmacistResponse> updatePharmacist(
            @PathVariable Long id,
            @Valid @RequestBody PharmacistRequest request) {
        return ResponseEntity.ok(pharmacistService.updatePharmacist(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePharmacist(@PathVariable Long id) {
        pharmacistService.deletePharmacist(id);
        return ResponseEntity.noContent().build();
    }
}
