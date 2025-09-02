package com.example.memberservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class MemberController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Member Service");
        response.put("port", "8082");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/members")
    public Map<String, Object> getMembers() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "No members available yet");
        response.put("count", 0);
        return response;
    }
}
