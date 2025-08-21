package com.example.eventservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class EventController {

    @GetMapping("/events")
    public ResponseEntity<List<Object>> getEvents() {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
