package com.example.eventservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    private String location;
    
    @Column(nullable = false)
    private LocalDateTime dateTime;
    
    private String clubName;
    
    private String status;
    
    private int maxCapacity;
    
    private int currentCapacity;
    
    // Constructors
    public Event() {
        this.status = "UPCOMING";
        this.currentCapacity = 0;
    }
    
    public Event(String name, String description, String location, LocalDateTime dateTime, String clubName, int maxCapacity) {
        this();
        this.name = name;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.clubName = clubName;
        this.maxCapacity = maxCapacity;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    
    public String getClubName() { return clubName; }
    public void setClubName(String clubName) { this.clubName = clubName; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    
    public int getCurrentCapacity() { return currentCapacity; }
    public void setCurrentCapacity(int currentCapacity) { this.currentCapacity = currentCapacity; }
    
    public boolean isFull() {
        return currentCapacity >= maxCapacity;
    }
    
    public int getAvailableSpots() {
        return maxCapacity - currentCapacity;
    }
}
