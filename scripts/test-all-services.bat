@echo off
echo ==========================================
echo Testing All ClubConnect Services
echo ==========================================
echo.

echo Testing Eureka Server (Port 8761)...
curl -s http://localhost:8761/actuator/health
echo.
echo.

echo Testing API Gateway (Port 8080)...
curl -s http://localhost:8080/actuator/health
echo.
echo.

echo Testing Club Service (Port 8081)...
curl -s http://localhost:8081/actuator/health
echo.
curl -s http://localhost:8081/clubs
echo.
echo.

echo Testing Member Service (Port 8082)...
curl -s http://localhost:8082/actuator/health
echo.
curl -s http://localhost:8082/members
echo.
echo.

echo Testing Event Service (Port 8083)...
curl -s http://localhost:8083/actuator/health
echo.
curl -s http://localhost:8083/events
echo.
echo.

echo Testing Registration Service (Port 8084)...
curl -s http://localhost:8084/actuator/health
echo.
curl -s http://localhost:8084/registrations
echo.
echo.

echo Testing Config Server (Port 8888)...
curl -s http://localhost:8888/actuator/health
echo.
echo.

echo ==========================================
echo Testing API Gateway Routing
echo ==========================================
echo.

echo Testing Club Service via Gateway...
curl -s http://localhost:8080/clubs
echo.
echo.

echo Testing Member Service via Gateway...
curl -s http://localhost:8080/members
echo.
echo.

echo Testing Event Service via Gateway...
curl -s http://localhost:8080/events
echo.
echo.

echo Testing Registration Service via Gateway...
curl -s http://localhost:8080/registrations
echo.
echo.

echo ==========================================
echo Testing Config Server Endpoints
echo ==========================================
echo.

echo Testing Club Service Configuration...
curl -s http://localhost:8888/club-service/default
echo.
echo.

echo Testing Member Service Configuration...
curl -s http://localhost:8888/member-service/default
echo.
echo.

echo Testing Event Service Configuration...
curl -s http://localhost:8888/event-service/default
echo.
echo.

echo Testing Registration Service Configuration...
curl -s http://localhost:8888/registration-service/default
echo.
echo.

echo ==========================================
echo Service Test Complete!
echo ==========================================
pause
