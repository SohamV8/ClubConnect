package com.example.clubservice.config;

import com.example.clubservice.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClubService clubService;

    @Override
    public void run(String... args) throws Exception {
        clubService.initializeSampleData();
    }
}
