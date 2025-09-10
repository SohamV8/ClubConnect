package com.example.memberservice.controller;

import com.example.memberservice.model.Member;
import com.example.memberservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/debug")
public class DebugController {
    
    @Autowired
    private MemberRepository memberRepository;
    
    @GetMapping("/test")
    public String test() {
        return "Debug controller is working!";
    }
    
    @GetMapping("/members")
    public List<Member> getMembers() {
        try {
            return memberRepository.findAll();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @PostMapping("/members")
    public Member createMember() {
        try {
            Member member = new Member();
            member.setName("Debug User");
            member.setEmail("debug@example.com");
            member.setPhone("+1234567890");
            member.setClubName("Debug Club");
            member.setJoinDate(LocalDateTime.now());
            member.setStatus("ACTIVE");
            
            return memberRepository.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
