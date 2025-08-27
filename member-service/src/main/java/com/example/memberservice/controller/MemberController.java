package com.example.memberservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/")
    public String home() {
        return "<h1>👥 Member Service</h1><p>Welcome to Member Management Service</p><p>Port: 8082</p>";
    }

    @GetMapping("/members")
    public String getMembers() {
        return "<h2>📋 Members List</h2><p>No members available yet</p>";
    }
}
