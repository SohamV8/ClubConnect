package com.example.registrationservice.service;

import com.example.registrationservice.dto.RegistrationDTO;
import com.example.registrationservice.model.Registration;
import com.example.registrationservice.repository.RegistrationRepository;
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
 * Enhanced Registration Service with inter-service communication and DTO mapping
 * Follows microservice best practices with proper error handling and service orchestration
 */
@Service
public class EnhancedRegistrationService {
    
    private static final Logger logger = LoggerFactory.getLogger(EnhancedRegistrationService.class);
    
    @Autowired
    private RegistrationRepository registrationRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private RestClient restClient;
    
    // Service discovery names
    private static final String MEMBER_SERVICE = "member-service";
    private static final String EVENT_SERVICE = "event-service";
    
    /**
     * Get all registrations with enriched data from member and event services
     * @return List of RegistrationDTOs with member and event information
     */
    public List<RegistrationDTO> getAllRegistrations() {
        logger.info("Fetching all registrations with enriched data");
        
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(this::enrichRegistrationWithRelatedData)
                .collect(Collectors.toList());
    }
    
    /**
     * Get registration by ID with enriched data
     * @param id Registration ID
     * @return Optional RegistrationDTO with enriched data
     */
    public Optional<RegistrationDTO> getRegistrationById(Long id) {
        logger.info("Fetching registration by ID: {}", id);
        
        return registrationRepository.findById(id)
                .map(this::enrichRegistrationWithRelatedData);
    }
    
    /**
     * Get registrations by member ID
     * @param memberId Member ID
     * @return List of RegistrationDTOs for the specified member
     */
    public List<RegistrationDTO> getRegistrationsByMember(Long memberId) {
        logger.info("Fetching registrations for member: {}", memberId);
        
        List<Registration> registrations = registrationRepository.findByMemberId(memberId);
        return registrations.stream()
                .map(this::enrichRegistrationWithRelatedData)
                .collect(Collectors.toList());
    }
    
    /**
     * Get registrations by event ID
     * @param eventId Event ID
     * @return List of RegistrationDTOs for the specified event
     */
    public List<RegistrationDTO> getRegistrationsByEvent(Long eventId) {
        logger.info("Fetching registrations for event: {}", eventId);
        
        List<Registration> registrations = registrationRepository.findByEventId(eventId);
        return registrations.stream()
                .map(this::enrichRegistrationWithRelatedData)
                .collect(Collectors.toList());
    }
    
    /**
     * Create a new registration with member and event validation
     * @param registrationDTO Registration data
     * @return Created RegistrationDTO with enriched data
     */
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO) {
        logger.info("Creating new registration for member {} and event {}", 
                registrationDTO.getMemberId(), registrationDTO.getEventId());
        
        // Validate member exists
        if (!validateMemberExists(registrationDTO.getMemberId())) {
            throw new RuntimeException("Member does not exist with ID: " + registrationDTO.getMemberId());
        }
        
        // Validate event exists
        if (!validateEventExists(registrationDTO.getEventId())) {
            throw new RuntimeException("Event does not exist with ID: " + registrationDTO.getEventId());
        }
        
        // Check if registration already exists
        if (registrationRepository.findByMemberIdAndEventId(
                registrationDTO.getMemberId(), registrationDTO.getEventId()).isPresent()) {
            throw new RuntimeException("Registration already exists for this member and event");
        }
        
        // Validate event capacity
        if (!validateEventCapacity(registrationDTO.getEventId())) {
            throw new RuntimeException("Event is full, cannot register");
        }
        
        // Convert DTO to entity
        Registration registration = modelMapper.map(registrationDTO, Registration.class);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("CONFIRMED");
        
        // Save to repository
        Registration savedRegistration = registrationRepository.save(registration);
        
        // Notify event service about capacity change
        notifyEventServiceCapacityChange(registrationDTO.getEventId(), "INCREMENT");
        
        // Enrich with related data
        return enrichRegistrationWithRelatedData(savedRegistration);
    }
    
    /**
     * Update registration status
     * @param id Registration ID
     * @param status New status
     * @return Updated RegistrationDTO with enriched data
     */
    public RegistrationDTO updateRegistrationStatus(Long id, String status) {
        logger.info("Updating registration status for ID: {} to {}", id, status);
        
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with ID: " + id));
        
        String oldStatus = registration.getStatus();
        registration.setStatus(status);
        
        // Save updated entity
        Registration updatedRegistration = registrationRepository.save(registration);
        
        // Handle capacity changes based on status change
        if ("CANCELLED".equals(status) && !"CANCELLED".equals(oldStatus)) {
            notifyEventServiceCapacityChange(registration.getEventId(), "DECREMENT");
        } else if ("CONFIRMED".equals(status) && "CANCELLED".equals(oldStatus)) {
            notifyEventServiceCapacityChange(registration.getEventId(), "INCREMENT");
        }
        
        // Enrich with related data
        return enrichRegistrationWithRelatedData(updatedRegistration);
    }
    
    /**
     * Delete a registration and update event capacity
     * @param id Registration ID
     */
    public void deleteRegistration(Long id) {
        logger.info("Deleting registration with ID: {}", id);
        
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with ID: " + id));
        
        // Notify event service about capacity change
        notifyEventServiceCapacityChange(registration.getEventId(), "DECREMENT");
        
        // Delete from repository
        registrationRepository.deleteById(id);
        
        logger.info("Successfully deleted registration for member {} and event {}", 
                registration.getMemberId(), registration.getEventId());
    }
    
    /**
     * Register a member for an event (convenience method)
     * @param memberId Member ID
     * @param eventId Event ID
     * @return Created RegistrationDTO
     */
    public RegistrationDTO registerMemberForEvent(Long memberId, Long eventId) {
        logger.info("Registering member {} for event {}", memberId, eventId);
        
        // Get member and event details for the registration
        String memberName = getMemberName(memberId);
        String eventName = getEventName(eventId);
        
        RegistrationDTO registrationDTO = new RegistrationDTO(memberId, eventId, memberName, eventName);
        return createRegistration(registrationDTO);
    }
    
    /**
     * Unregister a member from an event
     * @param memberId Member ID
     * @param eventId Event ID
     * @return true if unregistration successful, false otherwise
     */
    public boolean unregisterMemberFromEvent(Long memberId, Long eventId) {
        logger.info("Unregistering member {} from event {}", memberId, eventId);
        
        Optional<Registration> registration = registrationRepository.findByMemberIdAndEventId(memberId, eventId);
        
        if (registration.isPresent()) {
            deleteRegistration(registration.get().getId());
            return true;
        }
        
        return false;
    }
    
    /**
     * Enrich registration data with member and event information
     * @param registration Registration entity
     * @return Enriched RegistrationDTO
     */
    private RegistrationDTO enrichRegistrationWithRelatedData(Registration registration) {
        RegistrationDTO registrationDTO = modelMapper.map(registration, RegistrationDTO.class);
        
        try {
            // Get member details from member service
            Map<String, Object> memberData = getMemberDetails(registration.getMemberId());
            if (memberData != null) {
                registrationDTO.setMemberEmail(memberData.get("email").toString());
                registrationDTO.setMemberPhone(memberData.get("phone").toString());
            }
            
            // Get event details from event service
            Map<String, Object> eventData = getEventDetails(registration.getEventId());
            if (eventData != null) {
                registrationDTO.setEventLocation(eventData.get("location").toString());
                registrationDTO.setEventDateTime(LocalDateTime.parse(eventData.get("dateTime").toString()));
                registrationDTO.setClubName(eventData.get("clubName").toString());
                registrationDTO.setEventDescription(eventData.get("description").toString());
            }
            
        } catch (Exception e) {
            logger.warn("Failed to enrich registration data for registration: {}. Error: {}", 
                    registration.getId(), e.getMessage());
            // Set default values on failure
            registrationDTO.setMemberEmail("Email unavailable");
            registrationDTO.setMemberPhone("Phone unavailable");
            registrationDTO.setEventLocation("Location unavailable");
            registrationDTO.setClubName("Club information unavailable");
            registrationDTO.setEventDescription("Event description unavailable");
        }
        
        return registrationDTO;
    }
    
    /**
     * Get member details from member service
     * @param memberId Member ID
     * @return Map containing member details
     */
    private Map<String, Object> getMemberDetails(Long memberId) {
        try {
            String url = String.format("http://%s/members/%d", MEMBER_SERVICE, memberId);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            return response;
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch member details for member: {}. Error: {}", memberId, e.getMessage());
            return null;
        }
    }
    
    /**
     * Get event details from event service
     * @param eventId Event ID
     * @return Map containing event details
     */
    private Map<String, Object> getEventDetails(Long eventId) {
        try {
            String url = String.format("http://%s/events/%d", EVENT_SERVICE, eventId);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            return response;
            
        } catch (RestClientException e) {
            logger.error("Failed to fetch event details for event: {}. Error: {}", eventId, e.getMessage());
            return null;
        }
    }
    
    /**
     * Get member name from member service
     * @param memberId Member ID
     * @return Member name
     */
    private String getMemberName(Long memberId) {
        Map<String, Object> memberData = getMemberDetails(memberId);
        return memberData != null ? memberData.get("name").toString() : "Unknown Member";
    }
    
    /**
     * Get event name from event service
     * @param eventId Event ID
     * @return Event name
     */
    private String getEventName(Long eventId) {
        Map<String, Object> eventData = getEventDetails(eventId);
        return eventData != null ? eventData.get("name").toString() : "Unknown Event";
    }
    
    /**
     * Validate if a member exists by calling member service
     * @param memberId Member ID to validate
     * @return true if member exists, false otherwise
     */
    private boolean validateMemberExists(Long memberId) {
        try {
            String url = String.format("http://%s/members/validate/%d", MEMBER_SERVICE, memberId);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            
            return response != null && Boolean.TRUE.equals(response.get("exists"));
            
        } catch (RestClientException e) {
            logger.error("Failed to validate member existence: {}. Error: {}", memberId, e.getMessage());
            return false;
        }
    }
    
    /**
     * Validate if an event exists by calling event service
     * @param eventId Event ID to validate
     * @return true if event exists, false otherwise
     */
    private boolean validateEventExists(Long eventId) {
        try {
            String url = String.format("http://%s/events/validate/%d", EVENT_SERVICE, eventId);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            
            return response != null && Boolean.TRUE.equals(response.get("exists"));
            
        } catch (RestClientException e) {
            logger.error("Failed to validate event existence: {}. Error: {}", eventId, e.getMessage());
            return false;
        }
    }
    
    /**
     * Validate event capacity by calling event service
     * @param eventId Event ID
     * @return true if event has available capacity, false otherwise
     */
    private boolean validateEventCapacity(Long eventId) {
        try {
            String url = String.format("http://%s/events/%d/capacity", EVENT_SERVICE, eventId);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);
            
            if (response != null) {
                boolean isFull = Boolean.TRUE.equals(response.get("isFull"));
                return !isFull;
            }
            
        } catch (RestClientException e) {
            logger.error("Failed to validate event capacity for event: {}. Error: {}", eventId, e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Notify event service about capacity changes
     * @param eventId Event ID
     * @param action Capacity action (INCREMENT/DECREMENT)
     */
    private void notifyEventServiceCapacityChange(Long eventId, String action) {
        try {
            String url = String.format("http://%s/events/%d/capacity/%s", EVENT_SERVICE, eventId, action.toLowerCase());
            
            restClient.post()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity();
            
        } catch (RestClientException e) {
            logger.error("Failed to notify event service about capacity change for event {}. Error: {}", 
                    eventId, e.getMessage());
        }
    }
    
    /**
     * Clean up registrations when a member is deleted
     * @param memberId Member ID
     */
    public void cleanupRegistrationsByMember(Long memberId) {
        logger.info("Cleaning up registrations for deleted member: {}", memberId);
        
        try {
            List<Registration> registrations = registrationRepository.findByMemberId(memberId);
            
            // Notify event service about capacity changes
            for (Registration registration : registrations) {
                notifyEventServiceCapacityChange(registration.getEventId(), "DECREMENT");
            }
            
            registrationRepository.deleteAll(registrations);
            
            logger.info("Successfully cleaned up {} registrations for member: {}", registrations.size(), memberId);
            
        } catch (Exception e) {
            logger.error("Failed to cleanup registrations for member: {}. Error: {}", memberId, e.getMessage());
        }
    }
    
    /**
     * Clean up registrations when an event is deleted
     * @param eventId Event ID
     */
    public void cleanupRegistrationsByEvent(Long eventId) {
        logger.info("Cleaning up registrations for deleted event: {}", eventId);
        
        try {
            List<Registration> registrations = registrationRepository.findByEventId(eventId);
            registrationRepository.deleteAll(registrations);
            
            logger.info("Successfully cleaned up {} registrations for event: {}", registrations.size(), eventId);
            
        } catch (Exception e) {
            logger.error("Failed to cleanup registrations for event: {}. Error: {}", eventId, e.getMessage());
        }
    }
    
    /**
     * Clean up registrations when a club is deleted
     * @param clubName Club name
     */
    public void cleanupRegistrationsByClub(String clubName) {
        logger.info("Cleaning up registrations for deleted club: {}", clubName);
        
        try {
            // Get events for the club from event service
            String url = String.format("http://%s/events/club/%s", EVENT_SERVICE, clubName);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> events = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(List.class);
            
            if (events != null) {
                for (Map<String, Object> event : events) {
                    Long eventId = Long.valueOf(event.get("id").toString());
                    cleanupRegistrationsByEvent(eventId);
                }
            }
            
            logger.info("Successfully cleaned up registrations for club: {}", clubName);
            
        } catch (Exception e) {
            logger.error("Failed to cleanup registrations for club: {}. Error: {}", clubName, e.getMessage());
        }
    }
    
    /**
     * Get registration statistics
     * @return Map containing registration statistics
     */
    public Map<String, Object> getRegistrationStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            long totalRegistrations = registrationRepository.count();
            long confirmedRegistrations = registrationRepository.countByStatus("CONFIRMED");
            long cancelledRegistrations = registrationRepository.countByStatus("CANCELLED");
            
            stats.put("totalRegistrations", totalRegistrations);
            stats.put("confirmedRegistrations", confirmedRegistrations);
            stats.put("cancelledRegistrations", cancelledRegistrations);
            stats.put("cancellationRate", totalRegistrations > 0 ? 
                    (double) cancelledRegistrations / totalRegistrations * 100 : 0);
            
        } catch (Exception e) {
            logger.error("Failed to get registration statistics. Error: {}", e.getMessage());
            stats.put("error", "Failed to retrieve statistics");
        }
        
        return stats;
    }
    
    /**
     * Initialize sample data
     */
    public void initializeSampleData() {
        if (registrationRepository.count() == 0) {
            logger.info("Initializing sample registration data");
            
            Registration reg1 = new Registration(1L, 1L, "Alice Smith", "Java Workshop");
            Registration reg2 = new Registration(2L, 2L, "Bob Johnson", "Basketball Tournament");
            Registration reg3 = new Registration(3L, 3L, "Charlie Brown", "Art Exhibition");
            Registration reg4 = new Registration(4L, 4L, "Diana Prince", "Spring Boot Training");
            Registration reg5 = new Registration(5L, 5L, "Ethan Hunt", "Swimming Meet");
            
            registrationRepository.save(reg1);
            registrationRepository.save(reg2);
            registrationRepository.save(reg3);
            registrationRepository.save(reg4);
            registrationRepository.save(reg5);
            
            logger.info("Sample registration data initialized successfully");
        }
    }
}
