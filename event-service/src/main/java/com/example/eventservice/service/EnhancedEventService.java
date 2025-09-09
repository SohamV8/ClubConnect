package com.example.eventservice.service;

import com.example.eventservice.dto.EventDTO;
import com.example.eventservice.model.Event;
import com.example.eventservice.repository.EventRepository;
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
 * Enhanced Event Service with inter-service communication and DTO mapping
 * Follows microservice best practices with proper error handling and service orchestration
 */
@Service
public class EnhancedEventService {
    
    private static final Logger logger = LoggerFactory.getLogger(EnhancedEventService.class);
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private RestClient restClient;
    
    // Service discovery names
    private static final String CLUB_SERVICE = "club-service";
    private static final String REGISTRATION_SERVICE = "registration-service";
    
    /**
     * Get all events with enriched data from club service
     * @return List of EventDTOs with club information and capacity details
     */
    public List<EventDTO> getAllEvents() {
        logger.info("Fetching all events with enriched data");
        
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::enrichEventWithRelatedData)
                .collect(Collectors.toList());
    }
    
    /**
     * Get event by ID with enriched data from club service
     * @param id Event ID
     * @return Optional EventDTO with enriched data
     */
    public Optional<EventDTO> getEventById(Long id) {
        logger.info("Fetching event by ID: {}", id);
        
        return eventRepository.findById(id)
                .map(this::enrichEventWithRelatedData);
    }
    
    /**
     * Get events by club name
     * @param clubName Club name
     * @return List of EventDTOs for the specified club
     */
    public List<EventDTO> getEventsByClub(String clubName) {
        logger.info("Fetching events for club: {}", clubName);
        
        List<Event> events = eventRepository.findByClubName(clubName);
        return events.stream()
                .map(this::enrichEventWithRelatedData)
                .collect(Collectors.toList());
    }
    
    /**
     * Get upcoming events
     * @return List of upcoming EventDTOs
     */
    public List<EventDTO> getUpcomingEvents() {
        logger.info("Fetching upcoming events");
        
        List<Event> events = eventRepository.findByDateTimeAfter(LocalDateTime.now());
        return events.stream()
                .map(this::enrichEventWithRelatedData)
                .collect(Collectors.toList());
    }
    
    /**
     * Create a new event with club validation and capacity management
     * @param eventDTO Event data
     * @return Created EventDTO with enriched data
     */
    public EventDTO createEvent(EventDTO eventDTO) {
        logger.info("Creating new event: {}", eventDTO.getName());
        
        // Validate club exists
        if (!validateClubExists(eventDTO.getClubName())) {
            throw new RuntimeException("Club does not exist: " + eventDTO.getClubName());
        }
        
        // Validate event date is in the future
        if (eventDTO.getDateTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Event date must be in the future");
        }
        
        // Convert DTO to entity
        Event event = modelMapper.map(eventDTO, Event.class);
        event.setCurrentCapacity(0);
        event.setStatus("UPCOMING");
        
        // Save to repository
        Event savedEvent = eventRepository.save(event);
        
        // Enrich with related data
        return enrichEventWithRelatedData(savedEvent);
    }
    
    /**
     * Update an existing event with club validation and capacity management
     * @param id Event ID
     * @param eventDTO Updated event data
     * @return Updated EventDTO with enriched data
     */
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        logger.info("Updating event with ID: {}", id);
        
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));
        
        // Validate club exists if club name is being changed
        if (!eventDTO.getClubName().equals(existingEvent.getClubName()) && 
            !validateClubExists(eventDTO.getClubName())) {
            throw new RuntimeException("Club does not exist: " + eventDTO.getClubName());
        }
        
        // Validate capacity changes
        if (eventDTO.getMaxCapacity() < existingEvent.getCurrentCapacity()) {
            throw new RuntimeException("Cannot reduce capacity below current registrations: " + 
                    existingEvent.getCurrentCapacity());
        }
        
        // Update entity fields
        existingEvent.setName(eventDTO.getName());
        existingEvent.setDescription(eventDTO.getDescription());
        existingEvent.setLocation(eventDTO.getLocation());
        existingEvent.setDateTime(eventDTO.getDateTime());
        existingEvent.setClubName(eventDTO.getClubName());
        existingEvent.setMaxCapacity(eventDTO.getMaxCapacity());
        
        // Save updated entity
        Event updatedEvent = eventRepository.save(existingEvent);
        
        // Enrich with related data
        return enrichEventWithRelatedData(updatedEvent);
    }
    
    /**
     * Delete an event and clean up related data
     * @param id Event ID
     */
    public void deleteEvent(Long id) {
        logger.info("Deleting event with ID: {}", id);
        
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));
        
        // Clean up related data in other services
        cleanupRelatedData(event);
        
        // Delete from repository
        eventRepository.deleteById(id);
        
        logger.info("Successfully deleted event: {}", event.getName());
    }
    
    /**
     * Register a member for an event
     * @param eventId Event ID
     * @param memberId Member ID
     * @return true if registration successful, false otherwise
     */
    public boolean registerMemberForEvent(Long eventId, Long memberId) {
        logger.info("Registering member {} for event {}", memberId, eventId);
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
        
        // Check if event is full
        if (event.isFull()) {
            logger.warn("Event {} is full, cannot register member {}", eventId, memberId);
            return false;
        }
        
        // Check if event is still upcoming
        if (event.getDateTime().isBefore(LocalDateTime.now())) {
            logger.warn("Event {} has already passed, cannot register member {}", eventId, memberId);
            return false;
        }
        
        // Update capacity
        event.setCurrentCapacity(event.getCurrentCapacity() + 1);
        eventRepository.save(event);
        
        // Notify registration service
        notifyRegistrationService(eventId, memberId, "REGISTER");
        
        logger.info("Successfully registered member {} for event {}", memberId, eventId);
        return true;
    }
    
    /**
     * Unregister a member from an event
     * @param eventId Event ID
     * @param memberId Member ID
     * @return true if unregistration successful, false otherwise
     */
    public boolean unregisterMemberFromEvent(Long eventId, Long memberId) {
        logger.info("Unregistering member {} from event {}", memberId, eventId);
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
        
        // Update capacity
        if (event.getCurrentCapacity() > 0) {
            event.setCurrentCapacity(event.getCurrentCapacity() - 1);
            eventRepository.save(event);
        }
        
        // Notify registration service
        notifyRegistrationService(eventId, memberId, "UNREGISTER");
        
        logger.info("Successfully unregistered member {} from event {}", memberId, eventId);
        return true;
    }
    
    /**
     * Enrich event data with club information and capacity details
     * @param event Event entity
     * @return Enriched EventDTO
     */
    private EventDTO enrichEventWithRelatedData(Event event) {
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
        
        try {
            // Get club details from club service
            Map<String, Object> clubData = getClubDetails(event.getClubName());
            if (clubData != null) {
                eventDTO.setClubId(Long.valueOf(clubData.get("id").toString()));
                eventDTO.setClubDescription(clubData.get("description").toString());
                eventDTO.setClubCategory(clubData.get("category").toString());
            }
            
            // Calculate derived fields
            eventDTO.setAvailableSpots(event.getMaxCapacity() - event.getCurrentCapacity());
            eventDTO.setFull(event.isFull());
            
            // Update status based on date
            if (event.getDateTime().isBefore(LocalDateTime.now())) {
                eventDTO.setStatus("COMPLETED");
            } else if (event.getDateTime().isBefore(LocalDateTime.now().plusHours(1))) {
                eventDTO.setStatus("ONGOING");
            }
            
        } catch (Exception e) {
            logger.warn("Failed to enrich event data for event: {}. Error: {}", 
                    event.getName(), e.getMessage());
            // Set default values on failure
            eventDTO.setClubId(null);
            eventDTO.setClubDescription("Club information unavailable");
            eventDTO.setClubCategory("Unknown");
            eventDTO.setAvailableSpots(event.getMaxCapacity() - event.getCurrentCapacity());
            eventDTO.setFull(event.isFull());
        }
        
        return eventDTO;
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
     * Notify registration service about registration changes
     * @param eventId Event ID
     * @param memberId Member ID
     * @param action Registration action (REGISTER/UNREGISTER)
     */
    private void notifyRegistrationService(Long eventId, Long memberId, String action) {
        try {
            String url = String.format("http://%s/registrations/event/%d/member/%d/%s", 
                    REGISTRATION_SERVICE, eventId, memberId, action.toLowerCase());
            
            restClient.post()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity();
            
        } catch (RestClientException e) {
            logger.error("Failed to notify registration service for event {} and member {}. Error: {}", 
                    eventId, memberId, e.getMessage());
        }
    }
    
    /**
     * Clean up related data in other services when deleting an event
     * @param event Event to be deleted
     */
    private void cleanupRelatedData(Event event) {
        try {
            // Notify registration service to clean up event registrations
            String registrationUrl = String.format("http://%s/registrations/event/%d/cleanup", 
                    REGISTRATION_SERVICE, event.getId());
            restClient.post()
                    .uri(registrationUrl)
                    .retrieve()
                    .toBodilessEntity();
            
            logger.info("Successfully cleaned up related data for event: {}", event.getName());
            
        } catch (RestClientException e) {
            logger.error("Failed to cleanup related data for event: {}. Error: {}", 
                    event.getName(), e.getMessage());
            // Continue with deletion even if cleanup fails
        }
    }
    
    /**
     * Get event statistics
     * @param eventId Event ID
     * @return Map containing event statistics
     */
    public Map<String, Object> getEventStatistics(Long eventId) {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
            
            stats.put("eventName", event.getName());
            stats.put("clubName", event.getClubName());
            stats.put("maxCapacity", event.getMaxCapacity());
            stats.put("currentCapacity", event.getCurrentCapacity());
            stats.put("availableSpots", event.getMaxCapacity() - event.getCurrentCapacity());
            stats.put("isFull", event.isFull());
            stats.put("status", event.getStatus());
            
        } catch (Exception e) {
            logger.error("Failed to get statistics for event: {}. Error: {}", eventId, e.getMessage());
            stats.put("error", "Failed to retrieve statistics");
        }
        
        return stats;
    }
    
    /**
     * Clean up events when a club is deleted
     * @param clubName Club name
     */
    public void cleanupEventsByClub(String clubName) {
        logger.info("Cleaning up events for deleted club: {}", clubName);
        
        try {
            List<Event> events = eventRepository.findByClubName(clubName);
            for (Event event : events) {
                event.setClubName("UNASSIGNED");
                event.setStatus("CANCELLED");
            }
            eventRepository.saveAll(events);
            
            logger.info("Successfully cleaned up {} events for club: {}", events.size(), clubName);
            
        } catch (Exception e) {
            logger.error("Failed to cleanup events for club: {}. Error: {}", clubName, e.getMessage());
        }
    }
    
    /**
     * Initialize sample data
     */
    public void initializeSampleData() {
        if (eventRepository.count() == 0) {
            logger.info("Initializing sample event data");
            
            LocalDateTime now = LocalDateTime.now();
            
            Event event1 = new Event("Java Workshop", "Learn Java programming basics", "Room 101", 
                now.plusDays(7), "Tech Club", 30);
            Event event2 = new Event("Basketball Tournament", "Inter-club basketball competition", "Sports Complex", 
                now.plusDays(14), "Sports Club", 50);
            Event event3 = new Event("Art Exhibition", "Student artwork showcase", "Gallery Hall", 
                now.plusDays(21), "Arts Club", 100);
            Event event4 = new Event("Spring Boot Training", "Microservices with Spring Boot", "Lab 205", 
                now.plusDays(10), "Tech Club", 25);
            Event event5 = new Event("Swimming Meet", "Swimming competition", "Swimming Pool", 
                now.plusDays(28), "Sports Club", 40);
            
            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);
            eventRepository.save(event4);
            eventRepository.save(event5);
            
            logger.info("Sample event data initialized successfully");
        }
    }
}
