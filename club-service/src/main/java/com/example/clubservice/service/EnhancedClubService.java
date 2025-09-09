package com.example.clubservice.service;

import com.example.clubservice.dto.ClubDTO;
import com.example.clubservice.model.Club;
import com.example.clubservice.repository.ClubRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Enhanced Club Service with inter-service communication and DTO mapping
 * Follows microservice best practices with proper error handling and service orchestration
 */
@Service
public class EnhancedClubService {
    
    private static final Logger logger = LoggerFactory.getLogger(EnhancedClubService.class);
    
    @Autowired
    private ClubRepository clubRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private RestClient restClient;
    
    // Service discovery names
    private static final String MEMBER_SERVICE = "member-service";
    private static final String EVENT_SERVICE = "event-service";
    private static final String REGISTRATION_SERVICE = "registration-service";
    
    /**
     * Get all clubs with enriched data from other services
     * @return List of ClubDTOs with member and event counts
     */
    public List<ClubDTO> getAllClubs() {
        logger.info("Fetching all clubs with enriched data");
        
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream()
                .map(this::enrichClubWithRelatedData)
                .collect(Collectors.toList());
    }
    
    /**
     * Get club by ID with enriched data from other services
     * @param id Club ID
     * @return Optional ClubDTO with enriched data
     */
    public Optional<ClubDTO> getClubById(Long id) {
        logger.info("Fetching club by ID: {}", id);
        
        return clubRepository.findById(id)
                .map(this::enrichClubWithRelatedData);
    }
    
    /**
     * Get club by name with enriched data from other services
     * @param name Club name
     * @return Optional ClubDTO with enriched data
     */
    public Optional<ClubDTO> getClubByName(String name) {
        logger.info("Fetching club by name: {}", name);
        
        return Optional.ofNullable(clubRepository.findByName(name))
                .map(this::enrichClubWithRelatedData);
    }
    
    /**
     * Create a new club and update related services
     * @param clubDTO Club data
     * @return Created ClubDTO with enriched data
     */
    public ClubDTO createClub(ClubDTO clubDTO) {
        logger.info("Creating new club: {}", clubDTO.getName());
        
        // Convert DTO to entity
        Club club = modelMapper.map(clubDTO, Club.class);
        
        // Save to repository
        Club savedClub = clubRepository.save(club);
        
        // Enrich with related data
        return enrichClubWithRelatedData(savedClub);
    }
    
    /**
     * Update an existing club and sync with related services
     * @param id Club ID
     * @param clubDTO Updated club data
     * @return Updated ClubDTO with enriched data
     */
    public ClubDTO updateClub(Long id, ClubDTO clubDTO) {
        logger.info("Updating club with ID: {}", id);
        
        Club existingClub = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found with ID: " + id));
        
        // Update entity fields
        existingClub.setName(clubDTO.getName());
        existingClub.setDescription(clubDTO.getDescription());
        existingClub.setCategory(clubDTO.getCategory());
        
        // Save updated entity
        Club updatedClub = clubRepository.save(existingClub);
        
        // Enrich with related data
        return enrichClubWithRelatedData(updatedClub);
    }
    
    /**
     * Delete a club and clean up related data in other services
     * @param id Club ID
     */
    public void deleteClub(Long id) {
        logger.info("Deleting club with ID: {}", id);
        
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found with ID: " + id));
        
        // Clean up related data in other services
        cleanupRelatedData(club);
        
        // Delete from repository
        clubRepository.deleteById(id);
        
        logger.info("Successfully deleted club: {}", club.getName());
    }
    
    /**
     * Enrich club data with related information from other services
     * @param club Club entity
     * @return Enriched ClubDTO
     */
    private ClubDTO enrichClubWithRelatedData(Club club) {
        ClubDTO clubDTO = modelMapper.map(club, ClubDTO.class);
        
        try {
            // Get member IDs from member service
            List<Long> memberIds = getMemberIdsByClub(club.getName());
            clubDTO.setMemberIds(memberIds);
            clubDTO.setMemberCount(memberIds.size());
            
            // Get event IDs from event service
            List<Long> eventIds = getEventIdsByClub(club.getName());
            clubDTO.setEventIds(eventIds);
            clubDTO.setEventCount(eventIds.size());
            
        } catch (Exception e) {
            logger.warn("Failed to enrich club data for club: {}. Error: {}", 
                    club.getName(), e.getMessage());
            // Set default values on failure
            clubDTO.setMemberIds(Collections.emptyList());
            clubDTO.setMemberCount(0);
            clubDTO.setEventIds(Collections.emptyList());
            clubDTO.setEventCount(0);
        }
        
        return clubDTO;
    }
    
    /**
     * Get member IDs associated with a club from member service
     * @param clubName Club name
     * @return List of member IDs
     */
    private List<Long> getMemberIdsByClub(String clubName) {
        try {
            String url = String.format("http://%s/members/club/%s", MEMBER_SERVICE, clubName);
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> members = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(List.class);
            
            if (members != null) {
                return members.stream()
                        .map(member -> Long.valueOf(member.get("id").toString()))
                        .collect(Collectors.toList());
            }
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch members for club: {}. Error: {}", clubName, e.getMessage());
        }
        
        return Collections.emptyList();
    }
    
    /**
     * Get event IDs associated with a club from event service
     * @param clubName Club name
     * @return List of event IDs
     */
    private List<Long> getEventIdsByClub(String clubName) {
        try {
            String url = String.format("http://%s/events/club/%s", EVENT_SERVICE, clubName);
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> events = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(List.class);
            
            if (events != null) {
                return events.stream()
                        .map(event -> Long.valueOf(event.get("id").toString()))
                        .collect(Collectors.toList());
            }
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch events for club: {}. Error: {}", clubName, e.getMessage());
        }
        
        return Collections.emptyList();
    }
    
    /**
     * Clean up related data in other services when deleting a club
     * @param club Club to be deleted
     */
    private void cleanupRelatedData(Club club) {
        try {
            // Notify member service to update club references
            String memberUrl = String.format("http://%s/members/club/%s/cleanup", MEMBER_SERVICE, club.getName());
            restClient.post()
                    .uri(memberUrl)
                    .retrieve()
                    .toBodilessEntity();
            
            // Notify event service to update club references
            String eventUrl = String.format("http://%s/events/club/%s/cleanup", EVENT_SERVICE, club.getName());
            restClient.post()
                    .uri(eventUrl)
                    .retrieve()
                    .toBodilessEntity();
            
            // Notify registration service to clean up registrations
            String registrationUrl = String.format("http://%s/registrations/club/%s/cleanup", REGISTRATION_SERVICE, club.getName());
            restClient.post()
                    .uri(registrationUrl)
                    .retrieve()
                    .toBodilessEntity();
            
            logger.info("Successfully cleaned up related data for club: {}", club.getName());
            
        } catch (RestClientException e) {
            logger.error("Failed to cleanup related data for club: {}. Error: {}", 
                    club.getName(), e.getMessage());
            // Continue with deletion even if cleanup fails
        }
    }
    
    /**
     * Validate if a club exists
     * @param clubName Club name to validate
     * @return true if club exists, false otherwise
     */
    public boolean validateClubExists(String clubName) {
        return clubRepository.findByName(clubName) != null;
    }
    
    /**
     * Get club statistics
     * @param clubName Club name
     * @return Map containing club statistics
     */
    public Map<String, Object> getClubStatistics(String clubName) {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            Club club = clubRepository.findByName(clubName);
            if (club != null) {
                stats.put("clubName", club.getName());
                stats.put("memberCount", getMemberIdsByClub(clubName).size());
                stats.put("eventCount", getEventIdsByClub(clubName).size());
                stats.put("category", club.getCategory());
            }
        } catch (Exception e) {
            logger.error("Failed to get statistics for club: {}. Error: {}", clubName, e.getMessage());
            stats.put("error", "Failed to retrieve statistics");
        }
        
        return stats;
    }
    
    /**
     * Initialize sample data
     */
    public void initializeSampleData() {
        if (clubRepository.count() == 0) {
            logger.info("Initializing sample club data");
            
            Club techClub = new Club("Tech Club", "Technology enthusiasts club", "Technology");
            Club sportsClub = new Club("Sports Club", "Sports and fitness club", "Sports");
            Club artsClub = new Club("Arts Club", "Creative arts and culture", "Arts");
            
            clubRepository.save(techClub);
            clubRepository.save(sportsClub);
            clubRepository.save(artsClub);
            
            logger.info("Sample club data initialized successfully");
        }
    }
}
