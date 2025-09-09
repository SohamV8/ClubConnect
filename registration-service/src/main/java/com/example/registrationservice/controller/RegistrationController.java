package com.example.registrationservice.controller;

import com.example.registrationservice.dto.RegistrationDTO;
import com.example.registrationservice.service.EnhancedRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class RegistrationController {

    @Autowired
    private EnhancedRegistrationService enhancedRegistrationService;

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Registration Service");
        response.put("port", "8084");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/registrations")
    public List<RegistrationDTO> getRegistrations() {
        return enhancedRegistrationService.getAllRegistrations();
    }

    @GetMapping("/registrations/{id}")
    public RegistrationDTO getRegistrationById(@PathVariable Long id) {
        Optional<RegistrationDTO> registration = enhancedRegistrationService.getRegistrationById(id);
        return registration.orElse(null);
    }

    @GetMapping("/registrations/member/{memberId}")
    public List<RegistrationDTO> getRegistrationsByMember(@PathVariable Long memberId) {
        return enhancedRegistrationService.getRegistrationsByMember(memberId);
    }

    @GetMapping("/registrations/event/{eventId}")
    public List<RegistrationDTO> getRegistrationsByEvent(@PathVariable Long eventId) {
        return enhancedRegistrationService.getRegistrationsByEvent(eventId);
    }

    @PostMapping("/registrations")
    public RegistrationDTO createRegistration(@RequestBody RegistrationDTO registrationDTO) {
        return enhancedRegistrationService.createRegistration(registrationDTO);
    }

    @PostMapping("/registrations/register/{memberId}/{eventId}")
    public RegistrationDTO registerMemberForEvent(@PathVariable Long memberId, @PathVariable Long eventId) {
        return enhancedRegistrationService.registerMemberForEvent(memberId, eventId);
    }

    @PostMapping("/registrations/unregister/{memberId}/{eventId}")
    public Map<String, Object> unregisterMemberFromEvent(@PathVariable Long memberId, @PathVariable Long eventId) {
        boolean success = enhancedRegistrationService.unregisterMemberFromEvent(memberId, eventId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Unregistration successful" : "Unregistration failed");
        return response;
    }

    @PutMapping("/registrations/{id}/status")
    public RegistrationDTO updateRegistrationStatus(@PathVariable Long id, @RequestParam String status) {
        return enhancedRegistrationService.updateRegistrationStatus(id, status);
    }

    @DeleteMapping("/registrations/{id}")
    public Map<String, String> deleteRegistration(@PathVariable Long id) {
        enhancedRegistrationService.deleteRegistration(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Registration deleted successfully");
        return response;
    }

    @GetMapping("/registrations/statistics")
    public Map<String, Object> getRegistrationStatistics() {
        return enhancedRegistrationService.getRegistrationStatistics();
    }
}
