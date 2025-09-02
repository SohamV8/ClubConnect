package com.example.eventservice.repository;

import com.example.eventservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByClubName(String clubName);
    List<Event> findByStatus(String status);
    List<Event> findByDateTimeAfter(java.time.LocalDateTime dateTime);
}
