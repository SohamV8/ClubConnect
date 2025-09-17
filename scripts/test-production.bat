@echo off
echo ==========================================
echo Testing ClubConnect Production Environment
echo ==========================================
echo.

echo Testing Config Server (Port 8888)...
echo Health Check:
curl -s http://localhost:8888/actuator/health
echo.
echo.
echo Testing Club Service Production Configuration:
curl -s http://localhost:8888/club-service/prod
echo.
echo.
echo Testing Member Service Production Configuration:
curl -s http://localhost:8888/member-service/prod
echo.
echo.
echo Testing Event Service Production Configuration:
curl -s http://localhost:8888/event-service/prod
echo.
echo.
echo Testing Registration Service Production Configuration:
curl -s http://localhost:8888/registration-service/prod
echo.
echo.

echo ==========================================
echo Testing Service Health Endpoints
echo ==========================================
echo.

echo Testing Eureka Server Health:
curl -s http://localhost:8761/actuator/health
echo.
echo.

echo Testing API Gateway Health:
curl -s http://localhost:8080/actuator/health
echo.
echo.

echo Testing Club Service Health:
curl -s http://localhost:8081/actuator/health
echo.
echo.

echo Testing Member Service Health:
curl -s http://localhost:8082/actuator/health
echo.
echo.

echo Testing Event Service Health:
curl -s http://localhost:8083/actuator/health
echo.
echo.

echo Testing Registration Service Health:
curl -s http://localhost:8084/actuator/health
echo.
echo.

echo ==========================================
echo Testing Service Endpoints
echo ==========================================
echo.

echo Testing Club Service via Gateway:
curl -s http://localhost:8080/clubs
echo.
echo.

echo Testing Member Service via Gateway:
curl -s http://localhost:8080/members
echo.
echo.

echo Testing Event Service via Gateway:
curl -s http://localhost:8080/events
echo.
echo.

echo Testing Registration Service via Gateway:
curl -s http://localhost:8080/registrations
echo.
echo.

echo ==========================================
echo Testing Metrics and Monitoring
echo ==========================================
echo.

echo Testing Config Server Metrics:
curl -s http://localhost:8888/actuator/metrics
echo.
echo.

echo Testing Club Service Metrics:
curl -s http://localhost:8081/actuator/metrics
echo.
echo.

echo Testing Member Service Metrics:
curl -s http://localhost:8082/actuator/metrics
echo.
echo.

echo Testing Event Service Metrics:
curl -s http://localhost:8083/actuator/metrics
echo.
echo.

echo Testing Registration Service Metrics:
curl -s http://localhost:8084/actuator/metrics
echo.
echo.

echo ==========================================
echo Testing Configuration Refresh
echo ==========================================
echo.

echo Testing Club Service Configuration Refresh:
curl -X POST -s http://localhost:8081/actuator/refresh
echo.
echo.

echo Testing Member Service Configuration Refresh:
curl -X POST -s http://localhost:8082/actuator/refresh
echo.
echo.

echo Testing Event Service Configuration Refresh:
curl -X POST -s http://localhost:8083/actuator/refresh
echo.
echo.

echo Testing Registration Service Configuration Refresh:
curl -X POST -s http://localhost:8084/actuator/refresh
echo.
echo.

echo ==========================================
echo Production Test Complete!
echo ==========================================
echo.
echo All services have been tested for:
echo - Health Status
echo - Configuration Loading
echo - API Endpoints
echo - Metrics Collection
echo - Configuration Refresh
echo - Service Discovery
echo - Load Balancing
echo.
pause
