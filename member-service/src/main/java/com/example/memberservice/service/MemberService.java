package com.example.memberservice.service;

import com.example.memberservice.model.Member;
import com.example.memberservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;
    
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }
    
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
    
    public Member updateMember(Long id, Member memberDetails) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Member not found"));
        
        member.setName(memberDetails.getName());
        member.setEmail(memberDetails.getEmail());
        member.setPhone(memberDetails.getPhone());
        member.setClubName(memberDetails.getClubName());
        member.setStatus(memberDetails.getStatus());
        
        return memberRepository.save(member);
    }
    
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
    
    public List<Member> getMembersByClub(String clubName) {
        return memberRepository.findByClubName(clubName);
    }
    
    public void initializeSampleData() {
        if (memberRepository.count() == 0) {
            // Create sample members based on your data
            Member member1 = new Member("Alice Smith", "Alice2021@ncuindia.edu", "123-456-7890", "Tech Club");
            Member member2 = new Member("Bob Johnson", "Bob2022@ncuindia.edu", "234-567-8901", "Sports Club");
            Member member3 = new Member("Charlie Brown", "Charlie2023@ncuindia.edu", "345-678-9012", "Arts Club");
            Member member4 = new Member("Diana Prince", "Diana2021@ncuindia.edu", "456-789-0123", "Tech Club");
            Member member5 = new Member("Ethan Hunt", "Ethan2022@ncuindia.edu", "567-890-1234", "Sports Club");
            
            memberRepository.save(member1);
            memberRepository.save(member2);
            memberRepository.save(member3);
            memberRepository.save(member4);
            memberRepository.save(member5);
        }
    }
}
