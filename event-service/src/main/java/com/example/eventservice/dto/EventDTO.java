package com.example.eventservice.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Event entity
 * Used for API responses and inter-service communication
 */
public class EventDTO {
    
    private Long id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime dateTime;
    private String clubName;
    private String status;
    private int maxCapacity;
    private int currentCapacity;
    private int availableSpots;
    private Long clubId;
    private String clubDescription;
    private String clubCategory;
    private boolean isFull;
    
    // Constructors
    public EventDTO() {}
    
    public EventDTO(Long id, String name, String description, String location, LocalDateTime dateTime, 
                   String clubName, int maxCapacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.clubName = clubName;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = 0;
        this.status = "UPCOMING";
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getClubName() {
        return clubName;
    }
    
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getMaxCapacity() {
        return maxCapacity;
    }
    
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    public int getCurrentCapacity() {
        return currentCapacity;
    }
    
    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }
    
    public int getAvailableSpots() {
        return availableSpots;
    }
    
    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }
    
    public Long getClubId() {
        return clubId;
    }
    
    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }
    
    public String getClubDescription() {
        return clubDescription;
    }
    
    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
    
    public String getClubCategory() {
        return clubCategory;
    }
    
    public void setClubCategory(String clubCategory) {
        this.clubCategory = clubCategory;
    }
    
    public boolean isFull() {
        return isFull;
    }
    
    public void setFull(boolean full) {
        isFull = full;
    }
}
