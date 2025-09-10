package com.example.memberservice.controller;

import com.example.memberservice.dto.MemberDTO;
import com.example.memberservice.service.SimpleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class MemberController {

    @Autowired
    private SimpleMemberService simpleMemberService;

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
        return simpleMemberService.getAllMembers();
    }

    @GetMapping("/members/{id}")
    public MemberDTO getMemberById(@PathVariable Long id) {
        Optional<MemberDTO> member = simpleMemberService.getMemberById(id);
        return member.orElse(null);
    }

    @GetMapping("/members/email/{email}")
    public MemberDTO getMemberByEmail(@PathVariable String email) {
        Optional<MemberDTO> member = simpleMemberService.getMemberByEmail(email);
        return member.orElse(null);
    }

    @GetMapping("/members/club/{clubName}")
    public List<MemberDTO> getMembersByClub(@PathVariable String clubName) {
        return simpleMemberService.getMembersByClub(clubName);
    }

    @PostMapping("/members")
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO) {
        return simpleMemberService.createMember(memberDTO);
    }

    @PutMapping("/members/{id}")
    public MemberDTO updateMember(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        return simpleMemberService.updateMember(id, memberDTO);
    }

    @DeleteMapping("/members/{id}")
    public Map<String, String> deleteMember(@PathVariable Long id) {
        simpleMemberService.deleteMember(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Member deleted successfully");
        return response;
    }

    @GetMapping("/members/{id}/statistics")
    public Map<String, Object> getMemberStatistics(@PathVariable Long id) {
        return simpleMemberService.getMemberStatistics(id);
    }
}
