package com.example.clubservice.controller;

import com.example.clubservice.model.Club;
import com.example.clubservice.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
public class ClubController {

    @Autowired
    private ClubService clubService;
    
    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Club Service");
        response.put("port", "8081");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/clubs")
    public List<Club> getClubs() {
        return clubService.getAllClubs();
    }
    
    @GetMapping("/clubs/{id}")
    public Club getClub(@PathVariable Long id) {
        return clubService.getClubById(id).orElse(null);
    }
    
    @PostMapping("/clubs")
    public Club createClub(@RequestBody Club club) {
        return clubService.createClub(club);
    }
    
    @PutMapping("/clubs/{id}")
    public Club updateClub(@PathVariable Long id, @RequestBody Club club) {
        return clubService.updateClub(id, club);
    }
    
    @DeleteMapping("/clubs/{id}")
    public Map<String, String> deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Club deleted successfully");
        return response;
    }
}
