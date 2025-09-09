package com.example.memberservice.controller;

import com.example.memberservice.dto.MemberDTO;
import com.example.memberservice.service.EnhancedMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class MemberController {

    @Autowired
    private EnhancedMemberService enhancedMemberService;

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Member Service");
        response.put("port", "8082");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/members")
    public List<MemberDTO> getMembers() {
        return enhancedMemberService.getAllMembers();
    }

    @GetMapping("/members/{id}")
    public MemberDTO getMemberById(@PathVariable Long id) {
        Optional<MemberDTO> member = enhancedMemberService.getMemberById(id);
        return member.orElse(null);
    }

    @GetMapping("/members/email/{email}")
    public MemberDTO getMemberByEmail(@PathVariable String email) {
        Optional<MemberDTO> member = enhancedMemberService.getMemberByEmail(email);
        return member.orElse(null);
    }

    @GetMapping("/members/club/{clubName}")
    public List<MemberDTO> getMembersByClub(@PathVariable String clubName) {
        return enhancedMemberService.getMembersByClub(clubName);
    }

    @PostMapping("/members")
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO) {
        return enhancedMemberService.createMember(memberDTO);
    }

    @PutMapping("/members/{id}")
    public MemberDTO updateMember(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        return enhancedMemberService.updateMember(id, memberDTO);
    }

    @DeleteMapping("/members/{id}")
    public Map<String, String> deleteMember(@PathVariable Long id) {
        enhancedMemberService.deleteMember(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Member deleted successfully");
        return response;
    }

    @GetMapping("/members/{id}/statistics")
    public Map<String, Object> getMemberStatistics(@PathVariable Long id) {
        return enhancedMemberService.getMemberStatistics(id);
    }
}
