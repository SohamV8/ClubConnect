@echo off
echo ==========================================
echo Starting All ClubConnect Services
echo ==========================================
echo.

echo Step 1: Starting Eureka Server (Port 8761)...
start "Eureka Server" cmd /k "cd eureka-server && mvn spring-boot:run"
timeout 30
echo.

echo Step 2: Starting Config Server (Port 8888)...
start "Config Server" cmd /k "cd config-server && mvn spring-boot:run"
timeout 30
echo.

echo Step 3: Starting Club Service (Port 8081)...
start "Club Service" cmd /k "cd club-service && mvn spring-boot:run"
timeout 30
echo.

echo Step 4: Starting Member Service (Port 8082)...
start "Member Service" cmd /k "cd member-service && mvn spring-boot:run"
timeout 30
echo.

echo Step 5: Starting Event Service (Port 8083)...
start "Event Service" cmd /k "cd event-service && mvn spring-boot:run"
timeout 30
echo.

echo Step 6: Starting Registration Service (Port 8084)...
start "Registration Service" cmd /k "cd registration-service && mvn spring-boot:run"
timeout 30
echo.

echo Step 7: Starting API Gateway (Port 8080)...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
timeout 30
echo.

echo ==========================================
echo All Services Started!
echo ==========================================
echo.
echo Services should be running on:
echo - Eureka Server: http://localhost:8761
echo - Config Server: http://localhost:8888
echo - Club Service: http://localhost:8081
echo - Member Service: http://localhost:8082
echo - Event Service: http://localhost:8083
echo - Registration Service: http://localhost:8084
echo - API Gateway: http://localhost:8080
echo.
echo Wait 2-3 minutes for all services to fully start up and register with Eureka.
echo Then run the test script to verify everything is working.
echo.
pause
