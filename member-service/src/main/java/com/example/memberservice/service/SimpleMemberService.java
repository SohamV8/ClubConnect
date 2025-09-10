package com.example.memberservice.service;

import com.example.memberservice.dto.MemberDTO;
import com.example.memberservice.model.Member;
import com.example.memberservice.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple Member Service without inter-service communication
 * For testing CRUD operations
 */
@Service
public class SimpleMemberService {
    
    private static final Logger logger = LoggerFactory.getLogger(SimpleMemberService.class);
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Get all members (simple version)
     */
    public List<MemberDTO> getAllMembers() {
        logger.info("Fetching all members");
        
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }
    
    /**
     * Get member by ID
     */
    public Optional<MemberDTO> getMemberById(Long id) {
        logger.info("Fetching member by ID: {}", id);
        
        return memberRepository.findById(id)
                .map(member -> modelMapper.map(member, MemberDTO.class));
    }
    
    /**
     * Get member by email
     */
    public Optional<MemberDTO> getMemberByEmail(String email) {
        logger.info("Fetching member by email: {}", email);
        
        return memberRepository.findByEmail(email)
                .map(member -> modelMapper.map(member, MemberDTO.class));
    }
    
    /**
     * Get members by club
     */
    public List<MemberDTO> getMembersByClub(String clubName) {
        logger.info("Fetching members by club: {}", clubName);
        
        List<Member> members = memberRepository.findByClubName(clubName);
        return members.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }
    
    /**
     * Create new member (simple version)
     */
    public MemberDTO createMember(MemberDTO memberDTO) {
        logger.info("Creating new member: {}", memberDTO.getEmail());
        
        // Check if email already exists
        if (memberRepository.findByEmail(memberDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Member with email already exists: " + memberDTO.getEmail());
        }
        
        // Convert DTO to entity
        Member member = modelMapper.map(memberDTO, Member.class);
        member.setJoinDate(LocalDateTime.now());
        member.setStatus("ACTIVE");
        
        // Save to repository
        Member savedMember = memberRepository.save(member);
        
        // Convert back to DTO
        return modelMapper.map(savedMember, MemberDTO.class);
    }
    
    /**
     * Update member
     */
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        logger.info("Updating member: {}", id);
        
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        
        // Update fields
        existingMember.setName(memberDTO.getName());
        existingMember.setEmail(memberDTO.getEmail());
        existingMember.setPhone(memberDTO.getPhone());
        existingMember.setClubName(memberDTO.getClubName());
        existingMember.setStatus(memberDTO.getStatus());
        
        // Save updated member
        Member savedMember = memberRepository.save(existingMember);
        
        // Convert back to DTO
        return modelMapper.map(savedMember, MemberDTO.class);
    }
    
    /**
     * Delete member
     */
    public void deleteMember(Long id) {
        logger.info("Deleting member: {}", id);
        
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found with id: " + id);
        }
        
        memberRepository.deleteById(id);
    }
    
    /**
     * Get member statistics
     */
    public Map<String, Object> getMemberStatistics(Long id) {
        logger.info("Getting statistics for member: {}", id);
        
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("memberId", member.getId());
        stats.put("memberName", member.getName());
        stats.put("joinDate", member.getJoinDate());
        stats.put("status", member.getStatus());
        stats.put("clubName", member.getClubName());
        
        return stats;
    }
}
