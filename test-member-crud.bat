@echo off
echo Testing Member CRUD Operations...
echo.

echo 1. Testing Member Service Health...
curl -X GET http://localhost:8082/
echo.
echo.

echo 2. Testing Get All Members (should be empty)...
curl -X GET http://localhost:8082/members
echo.
echo.

echo 3. Testing Create Member...
curl -X POST http://localhost:8082/members ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"phone\":\"+1234567890\",\"clubName\":\"Tech Club\",\"status\":\"ACTIVE\"}"
echo.
echo.

echo 4. Testing Get All Members (should show 1 member)...
curl -X GET http://localhost:8082/members
echo.
echo.

echo 5. Testing Get Member by ID...
curl -X GET http://localhost:8082/members/1
echo.
echo.

echo Test completed!
pause
