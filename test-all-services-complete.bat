@echo off
echo ==========================================
echo Complete ClubConnect Services Test
echo ==========================================
echo.

echo Checking Service Status...
echo.

echo 1. Eureka Server Health...
curl -s http://localhost:8761/actuator/health
echo.
echo.

echo 2. Config Server Health...
curl -s http://localhost:8888/actuator/health
echo.
echo.

echo 3. Club Service Health...
curl -s http://localhost:8081/actuator/health
echo.
echo.

echo 4. Member Service Health...
curl -s http://localhost:8082/actuator/health
echo.
echo.

echo 5. Event Service Health...
curl -s http://localhost:8083/actuator/health
echo.
echo.

echo 6. Registration Service Health...
curl -s http://localhost:8084/actuator/health
echo.
echo.

echo 7. API Gateway Health...
curl -s http://localhost:8080/actuator/health
echo.
echo.

echo ==========================================
echo Testing CRUD Operations via Gateway
echo ==========================================
echo.

echo 8. GET All Clubs...
curl -s http://localhost:8080/clubs
echo.
echo.

echo 9. GET All Members...
curl -s http://localhost:8080/members
echo.
echo.

echo 10. GET All Events...
curl -s http://localhost:8080/events
echo.
echo.

echo 11. GET All Registrations...
curl -s http://localhost:8080/registrations
echo.
echo.

echo ==========================================
echo Testing Direct Service Access
echo ==========================================
echo.

echo 12. Direct Club Service...
curl -s http://localhost:8081/clubs
echo.
echo.

echo 13. Direct Member Service...
curl -s http://localhost:8082/members
echo.
echo.

echo 14. Direct Event Service...
curl -s http://localhost:8083/events
echo.
echo.

echo 15. Direct Registration Service...
curl -s http://localhost:8084/registrations
echo.
echo.

echo ==========================================
echo Testing POST Operations
echo ==========================================
echo.

echo 16. POST Create New Club...
curl -s -X POST http://localhost:8080/clubs -H "Content-Type: application/json" -d "{\"name\":\"New Tech Club\",\"description\":\"Latest technology club\",\"category\":\"Technology\"}"
echo.
echo.

echo 17. POST Create New Member...
curl -s -X POST http://localhost:8080/members -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"phone\":\"123-456-7890\",\"clubId\":1}"
echo.
echo.

echo 18. POST Create New Event...
curl -s -X POST http://localhost:8080/events -H "Content-Type: application/json" -d "{\"name\":\"New Workshop\",\"description\":\"Spring Boot Workshop\",\"date\":\"2025-12-01T10:00:00\",\"location\":\"Conference Room A\",\"clubId\":1}"
echo.
echo.

echo 19. POST Create New Registration...
curl -s -X POST http://localhost:8080/registrations -H "Content-Type: application/json" -d "{\"memberId\":1,\"eventId\":1,\"status\":\"PENDING\"}"
echo.
echo.

echo ==========================================
echo Testing PUT Operations
echo ==========================================
echo.

echo 20. PUT Update Club (ID: 1)...
curl -s -X PUT http://localhost:8080/clubs/1 -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"Updated Tech Club\",\"description\":\"Updated technology club\",\"category\":\"Technology\"}"
echo.
echo.

echo 21. PUT Update Member (ID: 1)...
curl -s -X PUT http://localhost:8080/members/1 -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"Updated John Doe\",\"email\":\"updated.john@example.com\",\"phone\":\"123-456-7890\",\"clubId\":1}"
echo.
echo.

echo 22. PUT Update Event (ID: 1)...
curl -s -X PUT http://localhost:8080/events/1 -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"Updated Workshop\",\"description\":\"Updated Spring Boot Workshop\",\"date\":\"2025-12-01T10:00:00\",\"location\":\"Conference Room B\",\"clubId\":1}"
echo.
echo.

echo ==========================================
echo Testing DELETE Operations
echo ==========================================
echo.

echo 23. DELETE Club (ID: 999 - should fail gracefully)...
curl -s -X DELETE http://localhost:8080/clubs/999
echo.
echo.

echo 24. DELETE Member (ID: 999 - should fail gracefully)...
curl -s -X DELETE http://localhost:8080/members/999
echo.
echo.

echo 25. DELETE Event (ID: 999 - should fail gracefully)...
curl -s -X DELETE http://localhost:8080/events/999
echo.
echo.

echo 26. DELETE Registration (ID: 999 - should fail gracefully)...
curl -s -X DELETE http://localhost:8080/registrations/999
echo.
echo.

echo ==========================================
echo Testing Config Server Endpoints
echo ==========================================
echo.

echo 27. Club Service Configuration...
curl -s http://localhost:8888/club-service/default
echo.
echo.

echo 28. Member Service Configuration...
curl -s http://localhost:8888/member-service/default
echo.
echo.

echo 29. Event Service Configuration...
curl -s http://localhost:8888/event-service/default
echo.
echo.

echo 30. Registration Service Configuration...
curl -s http://localhost:8888/registration-service/default
echo.
echo.

echo ==========================================
echo Eureka Dashboard Check
echo ==========================================
echo.

echo 31. Eureka Dashboard...
echo Open http://localhost:8761 in your browser to see registered services
echo.

echo ==========================================
echo Complete Test Finished!
echo ==========================================
echo.
echo Summary:
echo - All services have been tested
echo - Check the output above for any errors
echo - Services should return proper JSON responses
echo - Gateway routing should work for all services
echo - Direct service access should also work
echo - Config server should return configuration data
echo.
pause
