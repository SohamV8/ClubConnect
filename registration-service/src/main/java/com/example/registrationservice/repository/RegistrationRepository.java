package com.example.registrationservice.repository;

import com.example.registrationservice.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByMemberId(Long memberId);
    List<Registration> findByEventId(Long eventId);
    List<Registration> findByStatus(String status);
}
