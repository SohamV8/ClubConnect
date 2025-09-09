@echo off
echo ========================================
echo    ClubConnect Microservices with MySQL
echo    Password: 2004
echo ========================================
echo.

echo IMPORTANT: Make sure MySQL is running before starting services!
echo.

echo Step 1: Creating MySQL databases...
call create-databases.bat

echo.
echo Step 2: Starting Eureka Server...
start "Eureka Server" cmd /k "cd eureka-server && mvn spring-boot:run"

echo Waiting for Eureka to start...
timeout 30

echo Step 3: Starting API Gateway...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"

echo Step 4: Starting Club Service...
start "Club Service" cmd /k "cd club-service && mvn spring-boot:run"

echo Step 5: Starting Member Service...
start "Member Service" cmd /k "cd member-service && mvn spring-boot:run"

echo Step 6: Starting Event Service...
start "Event Service" cmd /k "cd event-service && mvn spring-boot:run"

echo Step 7: Starting Registration Service...
start "Registration Service" cmd /k "cd registration-service && mvn spring-boot:run"

echo.
echo ========================================
echo    All services are starting...
echo ========================================
echo.
echo Service URLs:
echo - Eureka Dashboard: http://localhost:8761
echo - API Gateway: http://localhost:8080
echo - Club Service: http://localhost:8081
echo - Member Service: http://localhost:8082
echo - Event Service: http://localhost:8083
echo - Registration Service: http://localhost:8084
echo.
echo Wait 60 seconds for all services to start, then test:
echo curl http://localhost:8081/clubs
echo curl http://localhost:8082/members
echo curl http://localhost:8083/events
echo curl http://localhost:8084/registrations
echo.
echo Press any key to open Eureka Dashboard...
pause > nul

echo Opening Eureka Dashboard...
start http://localhost:8761
