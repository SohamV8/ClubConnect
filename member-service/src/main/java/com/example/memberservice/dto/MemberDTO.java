package com.example.memberservice.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Member entity
 * Used for API responses and inter-service communication
 */
public class MemberDTO {
    
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String clubName;
    private LocalDateTime joinDate;
    private String status;
    private Long clubId;
    private String clubDescription;
    private String clubCategory;
    
    // Constructors
    public MemberDTO() {}
    
    public MemberDTO(Long id, String name, String email, String phone, String clubName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.clubName = clubName;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getClubName() {
        return clubName;
    }
    
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    
    public LocalDateTime getJoinDate() {
        return joinDate;
    }
    
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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
}
