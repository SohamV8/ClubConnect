package com.example.clubmanagement.service;

import com.example.clubmanagement.model.Club;
import com.example.clubmanagement.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    
    @Autowired
    private ClubRepository clubRepository;
    
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }
    
    public Club getClubById(Long id) {
        return clubRepository.findById(id).orElse(null);
    }
    
    public Club createClub(Club club) {
        return clubRepository.save(club);
    }
    
    public Club updateClub(Long id, Club club) {
        if (clubRepository.existsById(id)) {
            club.setId(id);
            return clubRepository.save(club);
        }
        return null;
    }
    
    public boolean deleteClub(Long id) {
        if (clubRepository.existsById(id)) {
            clubRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
