package com.example.registrationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class RegistrationController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Registration Service");
        response.put("port", "8084");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/registrations")
    public Map<String, Object> getRegistrations() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "No registrations available yet");
        response.put("count", 0);
        return response;
    }
}
