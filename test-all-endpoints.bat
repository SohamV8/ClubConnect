@echo off
echo ==========================================
echo Testing All ClubConnect Services and Endpoints
echo ==========================================
echo.

echo Checking Service Status...
echo.

echo 1. Testing Eureka Server (Port 8761)...
curl -s http://localhost:8761/actuator/health
echo.
echo.

echo 2. Testing API Gateway (Port 8080)...
curl -s http://localhost:8080/actuator/health
echo.
echo.

echo 3. Testing Club Service (Port 8081)...
curl -s http://localhost:8081/actuator/health
echo.
echo.

echo 4. Testing Member Service (Port 8082)...
curl -s http://localhost:8082/actuator/health
echo.
echo.

echo 5. Testing Event Service (Port 8083)...
curl -s http://localhost:8083/actuator/health
echo.
echo.

echo 6. Testing Registration Service (Port 8084)...
curl -s http://localhost:8084/actuator/health
echo.
echo.

echo 7. Testing Config Server (Port 8888)...
curl -s http://localhost:8888/actuator/health
echo.
echo.

echo ==========================================
echo Testing API Endpoints via Gateway
echo ==========================================
echo.

echo 8. Testing Club Service Endpoint...
curl -s http://localhost:8080/clubs
echo.
echo.

echo 9. Testing Member Service Endpoint...
curl -s http://localhost:8080/members
echo.
echo.

echo 10. Testing Event Service Endpoint...
curl -s http://localhost:8080/events
echo.
echo.

echo 11. Testing Registration Service Endpoint...
curl -s http://localhost:8080/registrations
echo.
echo.

echo ==========================================
echo Testing Direct Service Endpoints
echo ==========================================
echo.

echo 12. Testing Club Service Direct...
curl -s http://localhost:8081/clubs
echo.
echo.

echo 13. Testing Member Service Direct...
curl -s http://localhost:8082/members
echo.
echo.

echo 14. Testing Event Service Direct...
curl -s http://localhost:8083/events
echo.
echo.

echo 15. Testing Registration Service Direct...
curl -s http://localhost:8084/registrations
echo.
echo.

echo ==========================================
echo Testing Config Server Endpoints
echo ==========================================
echo.

echo 16. Testing Club Service Configuration...
curl -s http://localhost:8888/club-service/default
echo.
echo.

echo 17. Testing Member Service Configuration...
curl -s http://localhost:8888/member-service/default
echo.
echo.

echo 18. Testing Event Service Configuration...
curl -s http://localhost:8888/event-service/default
echo.
echo.

echo 19. Testing Registration Service Configuration...
curl -s http://localhost:8888/registration-service/default
echo.
echo.

echo ==========================================
echo Service Test Complete!
echo ==========================================
echo.
echo Summary:
echo - Check the output above for any errors
echo - All services should return health status
echo - API endpoints should return data or proper error messages
echo - Config server should return configuration data
echo.
pause
