@echo off
echo ==========================================
echo Testing All CRUD Operations - ClubConnect
echo ==========================================
echo.

echo Checking Service Status First...
echo.

echo 1. Testing Service Health...
curl -s http://localhost:8761/actuator/health
echo.
curl -s http://localhost:8080/actuator/health
echo.
curl -s http://localhost:8081/actuator/health
echo.
curl -s http://localhost:8082/actuator/health
echo.
curl -s http://localhost:8083/actuator/health
echo.
curl -s http://localhost:8084/actuator/health
echo.
curl -s http://localhost:8888/actuator/health
echo.
echo.

echo ==========================================
echo CLUB SERVICE CRUD OPERATIONS
echo ==========================================
echo.

echo 2. GET All Clubs...
curl -s http://localhost:8080/clubs
echo.
echo.

echo 3. GET Club by ID (1)...
curl -s http://localhost:8080/clubs/1
echo.
echo.

echo 4. POST Create New Club...
curl -s -X POST http://localhost:8080/clubs -H "Content-Type: application/json" -d "{\"name\":\"New Tech Club\",\"description\":\"Latest technology club\",\"category\":\"Technology\"}"
echo.
echo.

echo 5. PUT Update Club (1)...
curl -s -X PUT http://localhost:8080/clubs/1 -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"Updated Tech Club\",\"description\":\"Updated technology club\",\"category\":\"Technology\"}"
echo.
echo.

echo 6. DELETE Club (if exists)...
curl -s -X DELETE http://localhost:8080/clubs/999
echo.
echo.

echo ==========================================
echo MEMBER SERVICE CRUD OPERATIONS
echo ==========================================
echo.

echo 7. GET All Members...
curl -s http://localhost:8080/members
echo.
echo.

echo 8. GET Member by ID (1)...
curl -s http://localhost:8080/members/1
echo.
echo.

echo 9. POST Create New Member...
curl -s -X POST http://localhost:8080/members -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"phone\":\"123-456-7890\",\"clubId\":1}"
echo.
echo.

echo 10. PUT Update Member (1)...
curl -s -X PUT http://localhost:8080/members/1 -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"Updated John Doe\",\"email\":\"updated.john@example.com\",\"phone\":\"123-456-7890\",\"clubId\":1}"
echo.
echo.

echo 11. DELETE Member (if exists)...
curl -s -X DELETE http://localhost:8080/members/999
echo.
echo.

echo ==========================================
echo EVENT SERVICE CRUD OPERATIONS
echo ==========================================
echo.

echo 12. GET All Events...
curl -s http://localhost:8080/events
echo.
echo.

echo 13. GET Event by ID (1)...
curl -s http://localhost:8080/events/1
echo.
echo.

echo 14. POST Create New Event...
curl -s -X POST http://localhost:8080/events -H "Content-Type: application/json" -d "{\"name\":\"New Workshop\",\"description\":\"Spring Boot Workshop\",\"date\":\"2025-12-01T10:00:00\",\"location\":\"Conference Room A\",\"clubId\":1}"
echo.
echo.

echo 15. PUT Update Event (1)...
curl -s -X PUT http://localhost:8080/events/1 -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"Updated Workshop\",\"description\":\"Updated Spring Boot Workshop\",\"date\":\"2025-12-01T10:00:00\",\"location\":\"Conference Room B\",\"clubId\":1}"
echo.
echo.

echo 16. DELETE Event (if exists)...
curl -s -X DELETE http://localhost:8080/events/999
echo.
echo.

echo ==========================================
echo REGISTRATION SERVICE CRUD OPERATIONS
echo ==========================================
echo.

echo 17. GET All Registrations...
curl -s http://localhost:8080/registrations
echo.
echo.

echo 18. GET Registration by ID (1)...
curl -s http://localhost:8080/registrations/1
echo.
echo.

echo 19. POST Create New Registration...
curl -s -X POST http://localhost:8080/registrations -H "Content-Type: application/json" -d "{\"memberId\":1,\"eventId\":1,\"status\":\"PENDING\"}"
echo.
echo.

echo 20. PUT Update Registration (1)...
curl -s -X PUT http://localhost:8080/registrations/1 -H "Content-Type: application/json" -d "{\"id\":1,\"memberId\":1,\"eventId\":1,\"status\":\"CONFIRMED\"}"
echo.
echo.

echo 21. DELETE Registration (if exists)...
curl -s -X DELETE http://localhost:8080/registrations/999
echo.
echo.

echo ==========================================
echo DIRECT SERVICE ACCESS (Bypassing Gateway)
echo ==========================================
echo.

echo 22. Direct Club Service Access...
curl -s http://localhost:8081/clubs
echo.
echo.

echo 23. Direct Member Service Access...
curl -s http://localhost:8082/members
echo.
echo.

echo 24. Direct Event Service Access...
curl -s http://localhost:8083/events
echo.
echo.

echo 25. Direct Registration Service Access...
curl -s http://localhost:8084/registrations
echo.
echo.

echo ==========================================
echo CONFIG SERVER ENDPOINTS
echo ==========================================
echo.

echo 26. Config Server Health...
curl -s http://localhost:8888/actuator/health
echo.
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
echo CRUD Operations Test Complete!
echo ==========================================
echo.
echo Summary:
echo - All CRUD operations have been tested
echo - Check the output above for any errors
echo - Services should return proper JSON responses
echo - Gateway routing should work for all services
echo - Direct service access should also work
echo.
pause
