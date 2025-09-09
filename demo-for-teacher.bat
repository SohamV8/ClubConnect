@echo off
echo ========================================
echo    ClubConnect Demo for Teacher
echo ========================================
echo.

echo Step 1: Starting MySQL Service...
call mysql-service-manager.bat
echo.

echo Step 2: Starting All Services...
call start-all-services-final.bat
echo.

echo Step 3: Waiting for services to start...
timeout 60

echo Step 4: Checking all ports...
call check-ports.bat
echo.

echo Step 5: Testing all services...
call test-all-services-final.bat
echo.

echo Step 6: Opening Eureka Dashboard...
start http://localhost:8761

echo.
echo ========================================
echo    Demo Complete!
echo ========================================
echo.
echo What to show to teacher:
echo 1. Eureka Dashboard - http://localhost:8761
echo 2. API Gateway - http://localhost:8080
echo 3. Individual Services - Ports 8081-8084
echo 4. MySQL Databases - Use view-databases.bat
echo.
echo Key points to explain:
echo - Microservices Architecture
echo - Service Discovery (Eureka)
echo - API Gateway Pattern
echo - Database per Service
echo - Inter-Service Communication
echo.
pause
