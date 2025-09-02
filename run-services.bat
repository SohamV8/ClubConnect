@echo off
echo Starting all ClubConnect services...

echo.
echo Starting Club Service on port 8081...
start "Club Service" cmd /k "cd club-service && mvn spring-boot:run"

echo.
echo Starting Member Service on port 8082...
start "Member Service" cmd /k "cd member-service && mvn spring-boot:run"

echo.
echo Starting Event Service on port 8083...
start "Event Service" cmd /k "cd event-service && mvn spring-boot:run"

echo.
echo Starting Registration Service on port 8084...
start "Registration Service" cmd /k "cd registration-service && mvn spring-boot:run"

echo.
echo Starting Movie Service on port 9003...
start "Movie Service" cmd /k "cd Movie && mvn spring-boot:run"

echo.
echo All services are starting...
echo.
echo Service URLs:
echo - Club Service: http://localhost:8081
echo - Member Service: http://localhost:8082
echo - Event Service: http://localhost:8083
echo - Registration Service: http://localhost:8084
echo - Movie Service: http://localhost:9003
echo.
echo Press any key to exit...
pause > nul
