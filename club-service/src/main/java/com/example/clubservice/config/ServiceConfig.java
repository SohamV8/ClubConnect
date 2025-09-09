package com.example.clubservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configuration class for service dependencies
 * Provides beans for ModelMapper and RestClient
 */
@Configuration
public class ServiceConfig {
    
    /**
     * ModelMapper bean for entity-DTO conversion
     * @return ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    /**
     * RestClient bean for inter-service communication
     * @return RestClient instance
     */
    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}
