package com.courseregistration.controller;

import com.courseregistration.service.RegistrationService;
import com.courseregistration.contract.RegistrationResponse;
import com.courseregistration.model.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registrations")
    public ResponseEntity<List<RegistrationResponse>> getAllRegistrations() {
        List<RegistrationResponse> registrations = registrationService.getAllRegistrations();
        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/registrations/{id}")
    public ResponseEntity<RegistrationResponse> getRegistrationById(@PathVariable Long id) {
        RegistrationResponse registration = registrationService.getRegistrationById(id);
        return ResponseEntity.ok(registration);
    }

    @PostMapping("/registrations")
    public ResponseEntity<RegistrationResponse> addRegistration(@RequestBody Registration registration) {
        RegistrationResponse addedRegistration = registrationService.addRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRegistration);
    }

    @PutMapping("/registrations/{id}")
    public ResponseEntity<RegistrationResponse> updateRegistrationById(@PathVariable Long id, @RequestBody Registration updatedRegistration) {
        RegistrationResponse updatedRegistrations = registrationService.updateRegistrationById(id, updatedRegistration);
        return ResponseEntity.ok(updatedRegistrations);
    }

    @DeleteMapping("//registrations/{id}")
    public ResponseEntity<Void> deleteRegistrationById(@PathVariable Long id) {
        registrationService.deleteRegistrationById(id);
        return ResponseEntity.ok().build();
    }
}
