package com.example.clubmanagement.service;

import com.example.clubmanagement.model.Registration;
import com.example.clubmanagement.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    
    @Autowired
    private RegistrationRepository registrationRepository;
    
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }
    
    public Registration getRegistrationById(Long id) {
        return registrationRepository.findById(id).orElse(null);
    }
    
    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }
    
    public Registration updateRegistration(Long id, Registration registration) {
        if (registrationRepository.existsById(id)) {
            registration.setId(id);
            return registrationRepository.save(registration);
        }
        return null;
    }
    
    public boolean deleteRegistration(Long id) {
        if (registrationRepository.existsById(id)) {
            registrationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
