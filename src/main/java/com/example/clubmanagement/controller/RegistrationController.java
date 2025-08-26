package com.example.clubmanagement.controller;

import com.example.clubmanagement.model.Registration;
import com.example.clubmanagement.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;
    
    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
        Registration registration = registrationService.getRegistrationById(id);
        if (registration != null) {
            return ResponseEntity.ok(registration);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Registration> createRegistration(@RequestBody Registration registration) {
        return ResponseEntity.ok(registrationService.createRegistration(registration));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Registration> updateRegistration(@PathVariable Long id, @RequestBody Registration registration) {
        Registration updatedRegistration = registrationService.updateRegistration(id, registration);
        if (updatedRegistration != null) {
            return ResponseEntity.ok(updatedRegistration);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        if (registrationService.deleteRegistration(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
