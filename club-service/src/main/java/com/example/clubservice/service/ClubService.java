package com.example.clubservice.service;

import com.example.clubservice.model.Club;
import com.example.clubservice.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {
    
    @Autowired
    private ClubRepository clubRepository;
    
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }
    
    public Optional<Club> getClubById(Long id) {
        return clubRepository.findById(id);
    }
    
    public Club createClub(Club club) {
        return clubRepository.save(club);
    }
    
    public Club updateClub(Long id, Club clubDetails) {
        Club club = clubRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Club not found"));
        
        club.setName(clubDetails.getName());
        club.setDescription(clubDetails.getDescription());
        club.setCategory(clubDetails.getCategory());
        
        return clubRepository.save(club);
    }
    
    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }
    
    public void initializeSampleData() {
        if (clubRepository.count() == 0) {
            Club techClub = new Club("Tech Club", "Technology enthusiasts club", "Technology");
            Club sportsClub = new Club("Sports Club", "Sports and fitness club", "Sports");
            Club artsClub = new Club("Arts Club", "Creative arts and culture", "Arts");
            
            clubRepository.save(techClub);
            clubRepository.save(sportsClub);
            clubRepository.save(artsClub);
        }
    }
}
