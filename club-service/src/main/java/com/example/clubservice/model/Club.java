package com.example.clubservice.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clubs")
public class Club {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    private String category;
    
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Member> members;
    
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Event> events;
    
    // Constructors
    public Club() {}
    
    public Club(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }
    
    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }
}
