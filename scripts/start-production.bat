@echo off
echo ==========================================
echo Starting ClubConnect in Production Mode
echo ==========================================
echo.

echo Setting Production Environment Variables...
set SPRING_PROFILES_ACTIVE=prod
set CONFIG_REPO_URL=https://github.com/your-org/clubconnect-config-repo
set CONFIG_BRANCH=main
set DB_HOST=localhost
set DB_PORT=3306
set DB_USERNAME=root
set DB_PASSWORD=2004
set EUREKA_URL=http://localhost:8761/eureka/
set CONFIG_USERNAME=admin
set CONFIG_PASSWORD=admin123
set ENCRYPT_KEY=mySecretKey123456789012345678901234567890

echo.
echo Step 1: Starting Config Server (Port 8888) with Production Profile...
start "Config Server (Prod)" cmd /k "cd config-server && set SPRING_PROFILES_ACTIVE=prod && mvn spring-boot:run"
timeout /t 20 /nobreak > nul

echo Step 2: Starting Eureka Server (Port 8761)...
start "Eureka Server" cmd /k "cd eureka-server && mvn spring-boot:run"
timeout /t 15 /nobreak > nul

echo Step 3: Starting API Gateway (Port 8080)...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
timeout /t 10 /nobreak > nul

echo Step 4: Starting Club Service (Port 8081) with Production Profile...
start "Club Service (Prod)" cmd /k "cd club-service && set SPRING_PROFILES_ACTIVE=prod && mvn spring-boot:run"
timeout /t 5 /nobreak > nul

echo Step 5: Starting Member Service (Port 8082) with Production Profile...
start "Member Service (Prod)" cmd /k "cd member-service && set SPRING_PROFILES_ACTIVE=prod && mvn spring-boot:run"
timeout /t 5 /nobreak > nul

echo Step 6: Starting Event Service (Port 8083) with Production Profile...
start "Event Service (Prod)" cmd /k "cd event-service && set SPRING_PROFILES_ACTIVE=prod && mvn spring-boot:run"
timeout /t 5 /nobreak > nul

echo Step 7: Starting Registration Service (Port 8084) with Production Profile...
start "Registration Service (Prod)" cmd /k "cd registration-service && set SPRING_PROFILES_ACTIVE=prod && mvn spring-boot:run"

echo.
echo ==========================================
echo All services started in Production Mode!
echo ==========================================
echo.
echo Service URLs:
echo - Config Server: http://localhost:8888
echo - Eureka Dashboard: http://localhost:8761
echo - API Gateway: http://localhost:8080
echo - Club Service: http://localhost:8081
echo - Member Service: http://localhost:8082
echo - Event Service: http://localhost:8083
echo - Registration Service: http://localhost:8084
echo.
echo Production Features Enabled:
echo - Enhanced Security
echo - Production Database Settings
echo - Optimized Connection Pooling
echo - Comprehensive Logging
echo - Health Monitoring
echo - Metrics Collection
echo - Circuit Breaker Pattern
echo - Rate Limiting
echo.
echo Config Server Endpoints:
echo - /{application}/{profile}
echo - /{application}-{profile}.yml
echo - /{application}-{profile}.properties
echo.
echo Example: http://localhost:8888/club-service/prod
echo.
pause
