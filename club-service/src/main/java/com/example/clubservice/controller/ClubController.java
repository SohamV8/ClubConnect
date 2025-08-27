package com.example.clubservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClubController {

    @GetMapping("/")
    public String home() {
        return "<h1>🏆 Club Service</h1><p>Welcome to Club Management Service</p><p>Port: 8081</p>";
    }

    @GetMapping("/clubs")
    public String getClubs() {
        return "<h2>📋 Clubs List</h2><p>No clubs available yet</p>";
    }
}
