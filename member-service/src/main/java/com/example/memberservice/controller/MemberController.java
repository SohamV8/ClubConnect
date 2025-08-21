package com.example.memberservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class MemberController {

    @GetMapping("/members")
    public ResponseEntity<List<Object>> getMembers() {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
