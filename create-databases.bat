@echo off
echo ========================================
echo    Creating MySQL Databases for ClubConnect
echo ========================================
echo.

echo Testing MySQL connection...
mysql -u root -p2004 -e "SELECT VERSION();"

if %errorlevel% neq 0 (
    echo ERROR: Cannot connect to MySQL. Please check:
    echo 1. MySQL server is running
    echo 2. Password is correct (2004)
    echo 3. MySQL is installed and accessible
    pause
    exit /b 1
)

echo.
echo MySQL connection successful!
echo.

echo Creating databases...
mysql -u root -p2004 -e "CREATE DATABASE IF NOT EXISTS memberdb;"
mysql -u root -p2004 -e "CREATE DATABASE IF NOT EXISTS clubdb;"
mysql -u root -p2004 -e "CREATE DATABASE IF NOT EXISTS eventdb;"
mysql -u root -p2004 -e "CREATE DATABASE IF NOT EXISTS registrationdb;"

echo.
echo Verifying databases created...
mysql -u root -p2004 -e "SHOW DATABASES;"

echo.
echo ========================================
echo    Databases created successfully!
echo ========================================
echo.
echo Created databases:
echo - memberdb (for Member Service)
echo - clubdb (for Club Service)
echo - eventdb (for Event Service)
echo - registrationdb (for Registration Service)
echo.
echo You can now start your microservices!
echo.
pause
