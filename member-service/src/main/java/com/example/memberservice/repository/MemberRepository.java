package com.example.memberservice.repository;

import com.example.memberservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByClubName(String clubName);
    Member findByEmail(String email);
    List<Member> findByStatus(String status);
}
