@echo off
echo ==========================================
echo Starting ClubConnect with Config Server
echo ==========================================
echo.

echo Step 1: Starting Config Server (Port 8888)...
start "Config Server" cmd /k "cd config-server && mvn spring-boot:run"
timeout /t 15 /nobreak > nul

echo Step 2: Starting Eureka Server (Port 8761)...
start "Eureka Server" cmd /k "cd eureka-server && mvn spring-boot:run"
timeout /t 10 /nobreak > nul

echo Step 3: Starting API Gateway (Port 8080)...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
timeout /t 10 /nobreak > nul

echo Step 4: Starting Club Service (Port 8081)...
start "Club Service" cmd /k "cd club-service && mvn spring-boot:run"
timeout /t 5 /nobreak > nul

echo Step 5: Starting Member Service (Port 8082)...
start "Member Service" cmd /k "cd member-service && mvn spring-boot:run"
timeout /t 5 /nobreak > nul

echo Step 6: Starting Event Service (Port 8083)...
start "Event Service" cmd /k "cd event-service && mvn spring-boot:run"
timeout /t 5 /nobreak > nul

echo Step 7: Starting Registration Service (Port 8084)...
start "Registration Service" cmd /k "cd registration-service && mvn spring-boot:run"

echo.
echo ==========================================
echo All services started!
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
echo Config Server Endpoints:
echo - /{application}/{profile}
echo - /{application}/{profile}/{label}
echo - /{application}-{profile}.yml
echo - /{application}-{profile}.properties
echo.
echo Example: http://localhost:8888/club-service/default
echo.
pause
