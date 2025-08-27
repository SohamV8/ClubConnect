package com.example.clubservice.repository;

import com.example.clubservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByClubId(Long clubId);
    Member findByEmail(String email);
}
