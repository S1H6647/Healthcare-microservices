package com.healthcare.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.patient.dto.PatientRequest;
import com.healthcare.patient.entity.Patient;
import com.healthcare.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
    }

    @Test
    void registerPatient_ShouldReturnCreated_WhenValidRequest() throws Exception {
        PatientRequest request = new PatientRequest(
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "9876543210",
                LocalDate.of(1995, 5, 20),
                "Female",
                "456 Elm St"
        );

        mockMvc.perform(post("/api/patient/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));

        assertThat(patientRepository.findAll()).hasSize(1);
    }

    @Test
    void registerPatient_ShouldReturnBadRequest_WhenPhoneIsInvalid() throws Exception {
        PatientRequest request = new PatientRequest(
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "1234567890",
                LocalDate.of(1995, 5, 20),
                "Female",
                "456 Elm St"
        );

        mockMvc.perform(post("/api/patient/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPatient_ShouldReturnPatient_WhenExists() throws Exception {
        Patient patient = Patient.builder()
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .phone("1122334455")
                .dateOfBirth(LocalDate.of(1988, 12, 10))
                .gender("Female")
                .address("789 Pine St")
                .build();
        Patient saved = patientRepository.save(patient);

        mockMvc.perform(get("/api/patient/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void getPatient_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/patient/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePatient_ShouldReturnNoContent_WhenExists() throws Exception {
        Patient patient = Patient.builder()
                .firstName("Bob")
                .lastName("Builder")
                .email("bob@example.com")
                .phone("5555555555")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .gender("Male")
                .build();
        Patient saved = patientRepository.save(patient);

        mockMvc.perform(delete("/api/patient/{id}", saved.getId()))
                .andExpect(status().isNoContent());

        assertThat(patientRepository.existsById(saved.getId())).isFalse();
    }
}
