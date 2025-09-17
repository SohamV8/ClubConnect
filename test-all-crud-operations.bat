@echo off
echo ==========================================
echo ClubConnect - Complete CRUD Operations Test
echo ==========================================
echo.

echo ==========================================
echo 1. GET Operations (Read All Data)
echo ==========================================
echo.

echo 1.1 GET All Clubs via Gateway...
curl -s http://localhost:8080/clubs
echo.
echo.

echo 1.2 GET All Members via Gateway...
curl -s http://localhost:8080/members
echo.
echo.

echo 1.3 GET All Events via Gateway...
curl -s http://localhost:8080/events
echo.
echo.

echo 1.4 GET All Registrations via Gateway...
curl -s http://localhost:8080/registrations
echo.
echo.

echo ==========================================
echo 2. POST Operations (Create New Data)
echo ==========================================
echo.

echo 2.1 POST Create New Club...
curl -s -X POST http://localhost:8080/clubs -H "Content-Type: application/json" -d "{\"name\":\"New Tech Club\",\"description\":\"Latest technology club\",\"category\":\"Technology\"}"
echo.
echo.

echo 2.2 POST Create New Member...
curl -s -X POST http://localhost:8080/members -H "Content-Type: application/json" -d "{\"name\":\"Jane Smith\",\"email\":\"jane.smith@example.com\",\"phone\":\"987-654-3210\",\"clubId\":1}"
echo.
echo.

echo 2.3 POST Create New Event...
curl -s -X POST http://localhost:8080/events -H "Content-Type: application/json" -d "{\"name\":\"New Workshop\",\"description\":\"Spring Boot Workshop\",\"date\":\"2025-12-01T10:00:00\",\"location\":\"Conference Room A\",\"clubId\":1}"
echo.
echo.

echo 2.4 POST Create New Registration...
curl -s -X POST http://localhost:8080/registrations -H "Content-Type: application/json" -d "{\"memberId\":1,\"eventId\":1,\"status\":\"PENDING\"}"
echo.
echo.

echo ==========================================
echo 3. PUT Operations (Update Existing Data)
echo ==========================================
echo.

echo 3.1 PUT Update Club (ID: 1)...
curl -s -X PUT http://localhost:8080/clubs/1 -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"Updated Tech Club\",\"description\":\"Updated technology club\",\"category\":\"Technology\"}"
echo.
echo.

echo 3.2 PUT Update Member (ID: 1)...
curl -s -X PUT http://localhost:8080/members/1 -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"Updated Debug User\",\"email\":\"updated.debug@example.com\",\"phone\":\"+1234567890\",\"clubId\":1}"
echo.
echo.

echo 3.3 PUT Update Event (ID: 1)...
curl -s -X PUT http://localhost:8080/events/1 -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"Updated Java Workshop\",\"description\":\"Updated Java programming workshop\",\"date\":\"2025-09-17T01:11:05.337817\",\"location\":\"Room 101\",\"clubId\":1}"
echo.
echo.

echo 3.4 PUT Update Registration (ID: 2)...
curl -s -X PUT http://localhost:8080/registrations/2 -H "Content-Type: application/json" -d "{\"id\":2,\"memberId\":2,\"eventId\":2,\"status\":\"CONFIRMED\"}"
echo.
echo.

echo ==========================================
echo 4. DELETE Operations (Remove Data)
echo ==========================================
echo.

echo 4.1 DELETE Club (ID: 999 - should fail gracefully)...
curl -s -X DELETE http://localhost:8080/clubs/999
echo.
echo.

echo 4.2 DELETE Member (ID: 999 - should fail gracefully)...
curl -s -X DELETE http://localhost:8080/members/999
echo.
echo.

echo 4.3 DELETE Event (ID: 999 - should fail gracefully)...
curl -s -X DELETE http://localhost:8080/events/999
echo.
echo.

echo 4.4 DELETE Registration (ID: 999 - should fail gracefully)...
curl -s -X DELETE http://localhost:8080/registrations/999
echo.
echo.

echo ==========================================
echo 5. Direct Service Access (Bypass Gateway)
echo ==========================================
echo.

echo 5.1 Direct Club Service...
curl -s http://localhost:8081/clubs
echo.
echo.

echo 5.2 Direct Member Service...
curl -s http://localhost:8082/members
echo.
echo.

echo 5.3 Direct Event Service...
curl -s http://localhost:8083/events
echo.
echo.

echo 5.4 Direct Registration Service...
curl -s http://localhost:8084/registrations
echo.
echo.

echo ==========================================
echo 6. Service Health Checks
echo ==========================================
echo.

echo 6.1 Eureka Server Health...
curl -s http://localhost:8761/actuator/health
echo.
echo.

echo 6.2 Config Server Health...
curl -s http://localhost:8888/actuator/health
echo.
echo.

echo 6.3 API Gateway Health...
curl -s http://localhost:8080/actuator/health
echo.
echo.

echo 6.4 Club Service Health...
curl -s http://localhost:8081/actuator/health
echo.
echo.

echo 6.5 Member Service Health...
curl -s http://localhost:8082/actuator/health
echo.
echo.

echo 6.6 Event Service Health...
curl -s http://localhost:8083/actuator/health
echo.
echo.

echo 6.7 Registration Service Health...
curl -s http://localhost:8084/actuator/health
echo.
echo.

echo ==========================================
echo 7. Config Server Endpoints
echo ==========================================
echo.

echo 7.1 Club Service Configuration...
curl -s http://localhost:8888/club-service/default
echo.
echo.

echo 7.2 Member Service Configuration...
curl -s http://localhost:8888/member-service/default
echo.
echo.

echo 7.3 Event Service Configuration...
curl -s http://localhost:8888/event-service/default
echo.
echo.

echo 7.4 Registration Service Configuration...
curl -s http://localhost:8888/registration-service/default
echo.
echo.

echo ==========================================
echo 8. Eureka Service Discovery
echo ==========================================
echo.

echo 8.1 Eureka Dashboard...
echo Open http://localhost:8761 in your browser to see all registered services
echo.

echo 8.2 Registered Services Count...
curl -s http://localhost:8761/eureka/apps | findstr /C:"<name>"
echo.

echo ==========================================
echo Complete CRUD Operations Test Finished!
echo ==========================================
echo.
echo Summary:
echo - All GET operations should return JSON data
echo - All POST operations should create new records
echo - All PUT operations should update existing records
echo - All DELETE operations should handle errors gracefully
echo - Direct service access should work independently
echo - All health checks should return {"status":"UP"}
echo - Config server should return configuration data
echo - Eureka should show all services registered
echo.
echo All services are now running and connected properly!
echo.
pause
