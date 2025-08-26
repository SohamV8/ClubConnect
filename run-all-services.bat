@echo off
echo Starting all Club Management Services...
echo.

echo Starting Club Service on port 8081...
start "Club Service" cmd /k "cd club-service && mvn spring-boot:run"

echo Starting Member Service on port 8082...
start "Member Service" cmd /k "cd member-service && mvn spring-boot:run"

echo Starting Event Service on port 8083...
start "Event Service" cmd /k "cd event-service && mvn spring-boot:run"

echo Starting Registration Service on port 8084...
start "Registration Service" cmd /k "cd registration-service && mvn spring-boot:run"

echo.
echo All services are starting...
echo Club Service: http://localhost:8081
echo Member Service: http://localhost:8082
echo Event Service: http://localhost:8083
echo Registration Service: http://localhost:8084
echo.
echo Press any key to exit this launcher...
pause > nul
