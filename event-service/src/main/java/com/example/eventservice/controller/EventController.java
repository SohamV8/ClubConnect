package com.example.eventservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class EventController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Event Service");
        response.put("port", "8083");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/events")
    public Map<String, Object> getEvents() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "No events available yet");
        response.put("count", 0);
        return response;
    }
}
