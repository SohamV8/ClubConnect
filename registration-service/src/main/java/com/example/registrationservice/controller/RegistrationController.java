package com.example.registrationservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class RegistrationController {

    @GetMapping("/registrations")
    public ResponseEntity<List<Object>> getRegistrations() {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
