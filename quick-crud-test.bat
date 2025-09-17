@echo off
echo ==========================================
echo Quick CRUD Operations Test - ClubConnect
echo ==========================================
echo.

echo Testing Service Status...
echo.

echo 1. Eureka Server Health...
curl -s http://localhost:8761/actuator/health
echo.
echo.

echo 2. API Gateway Health...
curl -s http://localhost:8080/actuator/health
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

echo 7. Config Server Health...
curl -s http://localhost:8888/actuator/health
echo.
echo.

echo ==========================================
echo CRUD OPERATIONS TEST
echo ==========================================
echo.

echo 8. GET All Clubs (via Gateway)...
curl -s http://localhost:8080/clubs
echo.
echo.

echo 9. GET All Clubs (Direct)...
curl -s http://localhost:8081/clubs
echo.
echo.

echo 10. GET All Members (via Gateway)...
curl -s http://localhost:8080/members
echo.
echo.

echo 11. GET All Members (Direct)...
curl -s http://localhost:8082/members
echo.
echo.

echo 12. GET All Events (via Gateway)...
curl -s http://localhost:8080/events
echo.
echo.

echo 13. GET All Events (Direct)...
curl -s http://localhost:8083/events
echo.
echo.

echo 14. GET All Registrations (via Gateway)...
curl -s http://localhost:8080/registrations
echo.
echo.

echo 15. GET All Registrations (Direct)...
curl -s http://localhost:8084/registrations
echo.
echo.

echo ==========================================
echo POST OPERATIONS TEST
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
echo PUT OPERATIONS TEST
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
echo DELETE OPERATIONS TEST
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
echo Quick CRUD Test Complete!
echo ==========================================
echo.
echo Summary:
echo - All CRUD operations have been tested
echo - Check the output above for responses
echo - Services should return proper JSON or error messages
echo - Gateway routing should work for all services
echo.
pause
