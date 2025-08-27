package com.example.eventservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @GetMapping("/")
    public String home() {
        return "<h1>🎉 Event Service</h1><p>Welcome to Event Management Service</p><p>Port: 8083</p>";
    }

    @GetMapping("/events")
    public String getEvents() {
        return "<h2>📋 Events List</h2><p>No events available yet</p>";
    }
}
