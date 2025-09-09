package com.example.memberservice.service;

import com.example.memberservice.dto.MemberDTO;
import com.example.memberservice.model.Member;
import com.example.memberservice.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Enhanced Member Service with inter-service communication and DTO mapping
 * Follows microservice best practices with proper error handling and service orchestration
 */
@Service
public class EnhancedMemberService {
    
    private static final Logger logger = LoggerFactory.getLogger(EnhancedMemberService.class);
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private RestClient restClient;
    
    // Service discovery names
    private static final String CLUB_SERVICE = "club-service";
    private static final String REGISTRATION_SERVICE = "registration-service";
    
    /**
     * Get all members with enriched data from club service
     * @return List of MemberDTOs with club information
     */
    public List<MemberDTO> getAllMembers() {
        logger.info("Fetching all members with enriched data");
        
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(this::enrichMemberWithClubData)
                .collect(Collectors.toList());
    }
    
    /**
     * Get member by ID with enriched data from club service
     * @param id Member ID
     * @return Optional MemberDTO with enriched data
     */
    public Optional<MemberDTO> getMemberById(Long id) {
        logger.info("Fetching member by ID: {}", id);
        
        return memberRepository.findById(id)
                .map(this::enrichMemberWithClubData);
    }
    
    /**
     * Get member by email with enriched data from club service
     * @param email Member email
     * @return Optional MemberDTO with enriched data
     */
    public Optional<MemberDTO> getMemberByEmail(String email) {
        logger.info("Fetching member by email: {}", email);
        
        return memberRepository.findByEmail(email)
                .map(this::enrichMemberWithClubData);
    }
    
    /**
     * Get members by club name
     * @param clubName Club name
     * @return List of MemberDTOs for the specified club
     */
    public List<MemberDTO> getMembersByClub(String clubName) {
        logger.info("Fetching members for club: {}", clubName);
        
        List<Member> members = memberRepository.findByClubName(clubName);
        return members.stream()
                .map(this::enrichMemberWithClubData)
                .collect(Collectors.toList());
    }
    
    /**
     * Create a new member with club validation
     * @param memberDTO Member data
     * @return Created MemberDTO with enriched data
     */
    public MemberDTO createMember(MemberDTO memberDTO) {
        logger.info("Creating new member: {}", memberDTO.getEmail());
        
        // Validate club exists
        if (!validateClubExists(memberDTO.getClubName())) {
            throw new RuntimeException("Club does not exist: " + memberDTO.getClubName());
        }
        
        // Check if email already exists
        if (memberRepository.findByEmail(memberDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Member with email already exists: " + memberDTO.getEmail());
        }
        
        // Convert DTO to entity
        Member member = modelMapper.map(memberDTO, Member.class);
        member.setJoinDate(LocalDateTime.now());
        member.setStatus("ACTIVE");
        
        // Save to repository
        Member savedMember = memberRepository.save(member);
        
        // Enrich with club data
        return enrichMemberWithClubData(savedMember);
    }
    
    /**
     * Update an existing member with club validation
     * @param id Member ID
     * @param memberDTO Updated member data
     * @return Updated MemberDTO with enriched data
     */
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        logger.info("Updating member with ID: {}", id);
        
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));
        
        // Validate club exists if club name is being changed
        if (!memberDTO.getClubName().equals(existingMember.getClubName()) && 
            !validateClubExists(memberDTO.getClubName())) {
            throw new RuntimeException("Club does not exist: " + memberDTO.getClubName());
        }
        
        // Update entity fields
        existingMember.setName(memberDTO.getName());
        existingMember.setEmail(memberDTO.getEmail());
        existingMember.setPhone(memberDTO.getPhone());
        existingMember.setClubName(memberDTO.getClubName());
        existingMember.setStatus(memberDTO.getStatus());
        
        // Save updated entity
        Member updatedMember = memberRepository.save(existingMember);
        
        // Enrich with club data
        return enrichMemberWithClubData(updatedMember);
    }
    
    /**
     * Delete a member and clean up related data
     * @param id Member ID
     */
    public void deleteMember(Long id) {
        logger.info("Deleting member with ID: {}", id);
        
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));
        
        // Clean up related data in other services
        cleanupRelatedData(member);
        
        // Delete from repository
        memberRepository.deleteById(id);
        
        logger.info("Successfully deleted member: {}", member.getName());
    }
    
    /**
     * Enrich member data with club information from club service
     * @param member Member entity
     * @return Enriched MemberDTO
     */
    private MemberDTO enrichMemberWithClubData(Member member) {
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        
        try {
            // Get club details from club service
            Map<String, Object> clubData = getClubDetails(member.getClubName());
            if (clubData != null) {
                memberDTO.setClubId(Long.valueOf(clubData.get("id").toString()));
                memberDTO.setClubDescription(clubData.get("description").toString());
                memberDTO.setClubCategory(clubData.get("category").toString());
            }
            
        } catch (Exception e) {
            logger.warn("Failed to enrich member data for member: {}. Error: {}", 
                    member.getName(), e.getMessage());
            // Set default values on failure
            memberDTO.setClubId(null);
            memberDTO.setClubDescription("Club information unavailable");
            memberDTO.setClubCategory("Unknown");
        }
        
        return memberDTO;
    }
    
    /**
     * Get club details from club service
     * @param clubName Club name
     * @return Map containing club details
     */
    private Map<String, Object> getClubDetails(String clubName) {
        try {
            String url = String.format("http://%s/clubs/name/%s", CLUB_SERVICE, clubName);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            return response;
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch club details for club: {}. Error: {}", clubName, e.getMessage());
            return null;
        }
    }
    
    /**
     * Validate if a club exists by calling club service
     * @param clubName Club name to validate
     * @return true if club exists, false otherwise
     */
    private boolean validateClubExists(String clubName) {
        try {
            String url = String.format("http://%s/clubs/validate/%s", CLUB_SERVICE, clubName);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            
            return response != null && Boolean.TRUE.equals(response.get("exists"));
            
        } catch (RestClientException e) {
            logger.error("Failed to validate club existence: {}. Error: {}", clubName, e.getMessage());
            return false;
        }
    }
    
    /**
     * Clean up related data in other services when deleting a member
     * @param member Member to be deleted
     */
    private void cleanupRelatedData(Member member) {
        try {
            // Notify registration service to clean up member registrations
            String registrationUrl = String.format("http://%s/registrations/member/%d/cleanup", 
                    REGISTRATION_SERVICE, member.getId());
            restClient.post()
                    .uri(registrationUrl)
                    .retrieve()
                    .toBodilessEntity();
            
            logger.info("Successfully cleaned up related data for member: {}", member.getName());
            
        } catch (RestClientException e) {
            logger.error("Failed to cleanup related data for member: {}. Error: {}", 
                    member.getName(), e.getMessage());
            // Continue with deletion even if cleanup fails
        }
    }
    
    /**
     * Get member statistics
     * @param memberId Member ID
     * @return Map containing member statistics
     */
    public Map<String, Object> getMemberStatistics(Long memberId) {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));
            
            stats.put("memberName", member.getName());
            stats.put("clubName", member.getClubName());
            stats.put("joinDate", member.getJoinDate());
            stats.put("status", member.getStatus());
            
            // Get registration count from registration service
            int registrationCount = getRegistrationCount(memberId);
            stats.put("registrationCount", registrationCount);
            
        } catch (Exception e) {
            logger.error("Failed to get statistics for member: {}. Error: {}", memberId, e.getMessage());
            stats.put("error", "Failed to retrieve statistics");
        }
        
        return stats;
    }
    
    /**
     * Get registration count for a member from registration service
     * @param memberId Member ID
     * @return Number of registrations
     */
    private int getRegistrationCount(Long memberId) {
        try {
            String url = String.format("http://%s/registrations/member/%d/count", REGISTRATION_SERVICE, memberId);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            
            if (response != null && response.containsKey("count")) {
                return Integer.valueOf(response.get("count").toString());
            }
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch registration count for member: {}. Error: {}", memberId, e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Clean up members when a club is deleted
     * @param clubName Club name
     */
    public void cleanupMembersByClub(String clubName) {
        logger.info("Cleaning up members for deleted club: {}", clubName);
        
        try {
            List<Member> members = memberRepository.findByClubName(clubName);
            for (Member member : members) {
                member.setClubName("UNASSIGNED");
                member.setStatus("INACTIVE");
            }
            memberRepository.saveAll(members);
            
            logger.info("Successfully cleaned up {} members for club: {}", members.size(), clubName);
            
        } catch (Exception e) {
            logger.error("Failed to cleanup members for club: {}. Error: {}", clubName, e.getMessage());
        }
    }
    
    /**
     * Initialize sample data
     */
    public void initializeSampleData() {
        if (memberRepository.count() == 0) {
            logger.info("Initializing sample member data");
            
            Member member1 = new Member("Alice Smith", "Alice2021@ncuindia.edu", "123-456-7890", "Tech Club");
            Member member2 = new Member("Bob Johnson", "Bob2022@ncuindia.edu", "234-567-8901", "Sports Club");
            Member member3 = new Member("Charlie Brown", "Charlie2023@ncuindia.edu", "345-678-9012", "Arts Club");
            Member member4 = new Member("Diana Prince", "Diana2021@ncuindia.edu", "456-789-0123", "Tech Club");
            Member member5 = new Member("Ethan Hunt", "Ethan2022@ncuindia.edu", "567-890-1234", "Sports Club");
            
            memberRepository.save(member1);
            memberRepository.save(member2);
            memberRepository.save(member3);
            memberRepository.save(member4);
            memberRepository.save(member5);
            
            logger.info("Sample member data initialized successfully");
        }
    }
}
