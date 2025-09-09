@echo off
echo ========================================
echo    Testing All ClubConnect Services
echo    MySQL Password: 2004
echo ========================================
echo.

echo 1. Testing Eureka Dashboard
echo Open: http://localhost:8761
echo.

echo 2. Testing Club Service
curl http://localhost:8081/clubs
echo.
echo.

echo 3. Testing Member Service
curl http://localhost:8082/members
echo.
echo.

echo 4. Testing Event Service
curl http://localhost:8083/events
echo.
echo.

echo 5. Testing Registration Service
curl http://localhost:8084/registrations
echo.
echo.

echo 6. Testing API Gateway
curl http://localhost:8080/clubs
echo.
echo.

echo 7. Testing Inter-Service Communication
echo Creating club...
curl -X POST http://localhost:8081/clubs -H "Content-Type: application/json" -d "{\"name\":\"Tech Club\",\"description\":\"Technology enthusiasts\",\"category\":\"Technology\"}"
echo.
echo.

echo Creating member...
curl -X POST http://localhost:8082/members -H "Content-Type: application/json" -d "{\"name\":\"Alice Smith\",\"email\":\"alice@example.com\",\"phone\":\"123-456-7890\",\"clubName\":\"Tech Club\"}"
echo.
echo.

echo Creating event...
curl -X POST http://localhost:8083/events -H "Content-Type: application/json" -d "{\"name\":\"Java Workshop\",\"description\":\"Learn Java\",\"location\":\"Room 101\",\"dateTime\":\"2025-09-20T10:00:00\",\"clubName\":\"Tech Club\",\"maxCapacity\":30}"
echo.
echo.

echo Registering member for event...
curl -X POST http://localhost:8084/registrations -H "Content-Type: application/json" -d "{\"memberId\":1,\"eventId\":1,\"memberName\":\"Alice Smith\",\"eventName\":\"Java Workshop\"}"
echo.
echo.

echo 8. Verifying data in MySQL
echo Checking memberdb...
mysql -u root -p2004 -e "USE memberdb; SELECT * FROM members;"

echo Checking clubdb...
mysql -u root -p2004 -e "USE clubdb; SELECT * FROM clubs;"

echo Checking eventdb...
mysql -u root -p2004 -e "USE eventdb; SELECT * FROM events;"

echo Checking registrationdb...
mysql -u root -p2004 -e "USE registrationdb; SELECT * FROM registrations;"

echo.
echo ========================================
echo    All Tests Complete!
echo ========================================
echo.
echo Check your MySQL databases to see the data:
echo - memberdb.members
echo - clubdb.clubs
echo - eventdb.events
echo - registrationdb.registrations
echo.
pause
