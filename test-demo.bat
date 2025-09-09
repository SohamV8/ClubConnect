@echo off
echo ========================================
echo    ClubConnect CRUD Operations Demo
echo ========================================
echo.

echo 1. Testing Club Service...
echo GET /clubs
curl http://localhost:8081/clubs
echo.
echo.

echo 2. Testing Member Service...
echo GET /members
curl http://localhost:8082/members
echo.
echo.

echo 3. Testing Event Service...
echo GET /events
curl http://localhost:8083/events
echo.
echo.

echo 4. Testing Registration Service...
echo GET /registrations
curl http://localhost:8084/registrations
echo.
echo.

echo 5. Testing Member by Club...
echo GET /members/club/Tech%20Club
curl http://localhost:8082/members/club/Tech%20Club
echo.
echo.

echo 6. Testing Upcoming Events...
echo GET /events/upcoming
curl http://localhost:8083/events/upcoming
echo.
echo.

echo 7. Testing API Gateway...
echo GET /clubs via API Gateway
curl http://localhost:8080/clubs
echo.
echo.

echo ========================================
echo    Demo completed!
echo ========================================
echo.
echo Press any key to exit...
pause > nul
