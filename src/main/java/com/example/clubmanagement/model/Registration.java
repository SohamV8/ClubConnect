package com.example.clubmanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    
    @Column
    private String status;
    
    // Constructors
    public Registration() {}
    
    public Registration(Member member, Event event) {
        this.member = member;
        this.event = event;
        this.registrationDate = LocalDateTime.now();
        this.status = "ACTIVE";
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
