package com.example.clubservice.dto;

import java.util.List;

/**
 * Data Transfer Object for Club entity
 * Used for API responses and inter-service communication
 */
public class ClubDTO {
    
    private Long id;
    private String name;
    private String description;
    private String category;
    private List<Long> memberIds;
    private List<Long> eventIds;
    private int memberCount;
    private int eventCount;
    
    // Constructors
    public ClubDTO() {}
    
    public ClubDTO(Long id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
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
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public List<Long> getMemberIds() {
        return memberIds;
    }
    
    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
    
    public List<Long> getEventIds() {
        return eventIds;
    }
    
    public void setEventIds(List<Long> eventIds) {
        this.eventIds = eventIds;
    }
    
    public int getMemberCount() {
        return memberCount;
    }
    
    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
    
    public int getEventCount() {
        return eventCount;
    }
    
    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }
}
