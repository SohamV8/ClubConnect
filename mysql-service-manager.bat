@echo off
echo ========================================
echo    MySQL Service Manager
echo ========================================
echo.

echo 1. Starting MySQL Service...
net start mysql
if %errorlevel% equ 0 (
    echo ✅ MySQL Service started successfully
) else (
    echo ❌ Failed to start MySQL Service
    echo Please check if MySQL is installed
)
echo.

echo 2. Checking MySQL Service Status...
sc query mysql
echo.

echo 3. Testing MySQL Connection...
mysql -u root -p2004 -e "SELECT VERSION();" 2>nul
if %errorlevel% equ 0 (
    echo ✅ MySQL connection successful
) else (
    echo ❌ MySQL connection failed
    echo Please check your password (2004)
)
echo.

echo 4. Available MySQL Commands:
echo - Start: net start mysql
echo - Stop: net stop mysql
echo - Restart: net stop mysql && net start mysql
echo - Status: sc query mysql
echo.

echo 5. MySQL Workbench:
echo - Open MySQL Workbench
echo - Connect to localhost:3306
echo - Username: root, Password: 2004
echo.

echo ========================================
echo    MySQL Service Manager Complete
echo ========================================
echo.
pause
