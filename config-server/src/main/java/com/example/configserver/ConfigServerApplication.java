package com.example.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Spring Cloud Config Server Application
 * 
 * This application provides centralized configuration management for microservices.
 * It fetches configuration from a Git repository and serves it to client applications.
 * 
 * Features:
 * - Centralized configuration management
 * - Version control for configuration files
 * - Environment-specific configurations
 * - Dynamic configuration refresh
 * - Health monitoring and metrics
 * 
 * @author ClubConnect Team
 * @version 1.0
 * @since 2025-09-17
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    /**
     * Main method to start the Config Server application
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
        System.out.println("==========================================");
        System.out.println("🚀 Config Server started successfully!");
        System.out.println("📡 Server URL: http://localhost:8888");
        System.out.println("🔧 Health Check: http://localhost:8888/actuator/health");
        System.out.println("📋 Config Endpoints:");
        System.out.println("   - /{application}/{profile}");
        System.out.println("   - /{application}/{profile}/{label}");
        System.out.println("   - /{application}-{profile}.yml");
        System.out.println("   - /{application}-{profile}.properties");
        System.out.println("==========================================");
    }
}
