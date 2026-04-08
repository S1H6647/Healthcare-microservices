package com.healthcare.appointment.feign;

import com.healthcare.appointment.dto.DoctorResponse;
import com.healthcare.appointment.feign.fallback.DoctorClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "doctor-service",
        url = "${services.url.patient-service:http://localhost:8082}",
        fallback = DoctorClientFallback.class
)
public interface DoctorClient {

    @GetMapping("/api/doctors/{id}")
    DoctorResponse getDoctorById(@PathVariable Long id);
}
