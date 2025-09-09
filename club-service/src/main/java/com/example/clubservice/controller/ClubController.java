package com.example.clubservice.controller;

import com.example.clubservice.dto.ClubDTO;
import com.example.clubservice.service.EnhancedClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class ClubController {

    @Autowired
    private EnhancedClubService enhancedClubService;
    
    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Club Service");
        response.put("port", "8081");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/clubs")
    public List<ClubDTO> getClubs() {
        return enhancedClubService.getAllClubs();
    }
    
    @GetMapping("/clubs/{id}")
    public ClubDTO getClub(@PathVariable Long id) {
        Optional<ClubDTO> club = enhancedClubService.getClubById(id);
        return club.orElse(null);
    }

    @GetMapping("/clubs/name/{name}")
    public ClubDTO getClubByName(@PathVariable String name) {
        Optional<ClubDTO> club = enhancedClubService.getClubByName(name);
        return club.orElse(null);
    }

    @GetMapping("/clubs/validate/{name}")
    public Map<String, Object> validateClub(@PathVariable String name) {
        boolean exists = enhancedClubService.validateClubExists(name);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }
    
    @PostMapping("/clubs")
    public ClubDTO createClub(@RequestBody ClubDTO clubDTO) {
        return enhancedClubService.createClub(clubDTO);
    }
    
    @PutMapping("/clubs/{id}")
    public ClubDTO updateClub(@PathVariable Long id, @RequestBody ClubDTO clubDTO) {
        return enhancedClubService.updateClub(id, clubDTO);
    }
    
    @DeleteMapping("/clubs/{id}")
    public Map<String, String> deleteClub(@PathVariable Long id) {
        enhancedClubService.deleteClub(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Club deleted successfully");
        return response;
    }

    @GetMapping("/clubs/{name}/statistics")
    public Map<String, Object> getClubStatistics(@PathVariable String name) {
        return enhancedClubService.getClubStatistics(name);
    }
}
