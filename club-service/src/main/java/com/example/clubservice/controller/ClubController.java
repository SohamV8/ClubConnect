package com.example.clubservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ClubController {

    @GetMapping("/clubs")
    public ResponseEntity<List<Object>> getClubs() {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
