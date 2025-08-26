package com.example.clubmanagement.service;

import com.example.clubmanagement.model.Event;
import com.example.clubmanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
    
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    
    public Event updateEvent(Long id, Event event) {
        if (eventRepository.existsById(id)) {
            event.setId(id);
            return eventRepository.save(event);
        }
        return null;
    }
    
    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
