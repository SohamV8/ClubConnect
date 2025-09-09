package com.example.eventservice.controller;

import com.example.eventservice.dto.EventDTO;
import com.example.eventservice.service.EnhancedEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    private EnhancedEventService enhancedEventService;

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Event Service");
        response.put("port", "8083");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/events")
    public List<EventDTO> getEvents() {
        return enhancedEventService.getAllEvents();
    }

    @GetMapping("/events/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        Optional<EventDTO> event = enhancedEventService.getEventById(id);
        return event.orElse(null);
    }

    @GetMapping("/events/club/{clubName}")
    public List<EventDTO> getEventsByClub(@PathVariable String clubName) {
        return enhancedEventService.getEventsByClub(clubName);
    }

    @GetMapping("/events/upcoming")
    public List<EventDTO> getUpcomingEvents() {
        return enhancedEventService.getUpcomingEvents();
    }

    @PostMapping("/events")
    public EventDTO createEvent(@RequestBody EventDTO eventDTO) {
        return enhancedEventService.createEvent(eventDTO);
    }

    @PutMapping("/events/{id}")
    public EventDTO updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        return enhancedEventService.updateEvent(id, eventDTO);
    }

    @DeleteMapping("/events/{id}")
    public Map<String, String> deleteEvent(@PathVariable Long id) {
        enhancedEventService.deleteEvent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Event deleted successfully");
        return response;
    }

    @PostMapping("/events/{eventId}/register/{memberId}")
    public Map<String, Object> registerMemberForEvent(@PathVariable Long eventId, @PathVariable Long memberId) {
        boolean success = enhancedEventService.registerMemberForEvent(eventId, memberId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Registration successful" : "Registration failed");
        return response;
    }

    @PostMapping("/events/{eventId}/unregister/{memberId}")
    public Map<String, Object> unregisterMemberFromEvent(@PathVariable Long eventId, @PathVariable Long memberId) {
        boolean success = enhancedEventService.unregisterMemberFromEvent(eventId, memberId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Unregistration successful" : "Unregistration failed");
        return response;
    }

    @GetMapping("/events/{id}/statistics")
    public Map<String, Object> getEventStatistics(@PathVariable Long id) {
        return enhancedEventService.getEventStatistics(id);
    }
}
