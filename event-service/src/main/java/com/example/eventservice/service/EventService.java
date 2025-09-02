package com.example.eventservice.service;

import com.example.eventservice.model.Event;
import com.example.eventservice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        event.setName(eventDetails.getName());
        event.setDescription(eventDetails.getDescription());
        event.setLocation(eventDetails.getLocation());
        event.setDateTime(eventDetails.getDateTime());
        event.setClubName(eventDetails.getClubName());
        event.setMaxCapacity(eventDetails.getMaxCapacity());
        
        return eventRepository.save(event);
    }
    
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    
    public List<Event> getEventsByClub(String clubName) {
        return eventRepository.findByClubName(clubName);
    }
    
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByDateTimeAfter(LocalDateTime.now());
    }
    
    public void initializeSampleData() {
        if (eventRepository.count() == 0) {
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
        }
    }
}
