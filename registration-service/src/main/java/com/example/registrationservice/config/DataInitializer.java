package com.example.registrationservice.config;

import com.example.registrationservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RegistrationService registrationService;

    @Override
    public void run(String... args) throws Exception {
        registrationService.initializeSampleData();
    }
}
