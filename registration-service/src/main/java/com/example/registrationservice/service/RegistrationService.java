package com.example.registrationservice.service;

import com.example.registrationservice.model.Registration;
import com.example.registrationservice.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    
    @Autowired
    private RegistrationRepository registrationRepository;
    
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }
    
    public Optional<Registration> getRegistrationById(Long id) {
        return registrationRepository.findById(id);
    }
    
    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }
    
    public Registration updateRegistration(Long id, Registration registrationDetails) {
        Registration registration = registrationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        registration.setStatus(registrationDetails.getStatus());
        
        return registrationRepository.save(registration);
    }
    
    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }
    
    public List<Registration> getRegistrationsByMember(Long memberId) {
        return registrationRepository.findByMemberId(memberId);
    }
    
    public List<Registration> getRegistrationsByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }
    
    public void initializeSampleData() {
        if (registrationRepository.count() == 0) {
            // Create sample registrations
            Registration reg1 = new Registration(1L, 1L, "Alice Smith", "Java Workshop");
            Registration reg2 = new Registration(2L, 2L, "Bob Johnson", "Basketball Tournament");
            Registration reg3 = new Registration(3L, 3L, "Charlie Brown", "Art Exhibition");
            Registration reg4 = new Registration(4L, 4L, "Diana Prince", "Spring Boot Training");
            Registration reg5 = new Registration(5L, 5L, "Ethan Hunt", "Swimming Meet");
            
            registrationRepository.save(reg1);
            registrationRepository.save(reg2);
            registrationRepository.save(reg3);
            registrationRepository.save(reg4);
            registrationRepository.save(reg5);
        }
    }
}
