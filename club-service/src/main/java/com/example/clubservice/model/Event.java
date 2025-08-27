package com.example.clubservice.model;

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
    
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
    
    // Constructors
    public Event() {}
    
    public Event(String name, String description, String location, LocalDateTime dateTime) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
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
    
    public Club getClub() { return club; }
    public void setClub(Club club) { this.club = club; }
}
