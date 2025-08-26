package com.example.clubmanagement.service;

import com.example.clubmanagement.model.Member;
import com.example.clubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;
    
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }
    
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
    
    public Member updateMember(Long id, Member member) {
        if (memberRepository.existsById(id)) {
            member.setId(id);
            return memberRepository.save(member);
        }
        return null;
    }
    
    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
