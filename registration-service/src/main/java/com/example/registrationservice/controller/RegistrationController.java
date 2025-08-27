package com.example.registrationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @GetMapping("/")
    public String home() {
        return "<h1>📝 Registration Service</h1><p>Welcome to Registration Management Service</p><p>Port: 8084</p>";
    }

    @GetMapping("/registrations")
    public String getRegistrations() {
        return "<h2>📋 Registrations List</h2><p>No registrations available yet</p>";
    }
}
