@echo off
echo ==========================================
echo ClubConnect Services Status Check
echo ==========================================
echo.

echo Checking Service Ports...
echo.

echo 1. Eureka Server (8761)...
netstat -an | findstr ":8761"
echo.

echo 2. API Gateway (8080)...
netstat -an | findstr ":8080"
echo.

echo 3. Club Service (8081)...
netstat -an | findstr ":8081"
echo.

echo 4. Member Service (8082)...
netstat -an | findstr ":8082"
echo.

echo 5. Event Service (8083)...
netstat -an | findstr ":8083"
echo.

echo 6. Registration Service (8084)...
netstat -an | findstr ":8084"
echo.

echo 7. Config Server (8888)...
netstat -an | findstr ":8888"
echo.

echo ==========================================
echo Testing Service Health...
echo ==========================================
echo.

echo 8. Eureka Server Health...
curl -s http://localhost:8761/actuator/health
echo.
echo.

echo 9. API Gateway Health...
curl -s http://localhost:8080/actuator/health
echo.
echo.

echo 10. Club Service Health...
curl -s http://localhost:8081/actuator/health
echo.
echo.

echo 11. Member Service Health...
curl -s http://localhost:8082/actuator/health
echo.
echo.

echo 12. Event Service Health...
curl -s http://localhost:8083/actuator/health
echo.
echo.

echo 13. Registration Service Health...
curl -s http://localhost:8084/actuator/health
echo.
echo.

echo 14. Config Server Health...
curl -s http://localhost:8888/actuator/health
echo.
echo.

echo ==========================================
echo Testing API Gateway Endpoints...
echo ==========================================
echo.

echo 15. GET /clubs...
curl -s http://localhost:8080/clubs
echo.
echo.

echo 16. GET /members...
curl -s http://localhost:8080/members
echo.
echo.

echo 17. GET /events...
curl -s http://localhost:8080/events
echo.
echo.

echo 18. GET /registrations...
curl -s http://localhost:8080/registrations
echo.
echo.

echo ==========================================
echo Status Check Complete!
echo ==========================================
echo.
echo Summary:
echo - Check the port status above
echo - Services should show LISTENING status
echo - Health checks should return {"status":"UP"}
echo - API Gateway should route to services or show 503 if services are down
echo.
pause
