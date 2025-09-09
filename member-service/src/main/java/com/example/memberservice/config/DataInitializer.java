package com.example.memberservice.config;

import com.example.memberservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MemberService memberService;

    @Override
    public void run(String... args) throws Exception {
        memberService.initializeSampleData();
    }
}
