@echo off
echo ==========================================
echo Testing Config Server Integration
echo ==========================================
echo.

echo Testing Config Server Health...
curl -s http://localhost:8888/actuator/health
echo.
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

echo Testing Club Service YAML Configuration...
curl -s http://localhost:8888/club-service-default.yml
echo.
echo.

echo Testing Member Service Properties Configuration...
curl -s http://localhost:8888/member-service-default.properties
echo.
echo.

echo ==========================================
echo Config Server Test Complete!
echo ==========================================
pause
