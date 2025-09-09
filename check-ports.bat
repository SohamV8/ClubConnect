@echo off
echo ========================================
echo    ClubConnect Port Verification
echo ========================================
echo.

echo Checking MySQL Server (Port 3306)...
netstat -an | findstr ":3306" >nul
if %errorlevel% equ 0 (
    echo ✅ MySQL Server is running on port 3306
) else (
    echo ❌ MySQL Server is NOT running on port 3306
    echo Please start MySQL service first
)
echo.

echo Checking Eureka Server (Port 8761)...
netstat -an | findstr ":8761" >nul
if %errorlevel% equ 0 (
    echo ✅ Eureka Server is running on port 8761
) else (
    echo ❌ Eureka Server is NOT running on port 8761
)
echo.

echo Checking API Gateway (Port 8080)...
netstat -an | findstr ":8080" >nul
if %errorlevel% equ 0 (
    echo ✅ API Gateway is running on port 8080
) else (
    echo ❌ API Gateway is NOT running on port 8080
)
echo.

echo Checking Club Service (Port 8081)...
netstat -an | findstr ":8081" >nul
if %errorlevel% equ 0 (
    echo ✅ Club Service is running on port 8081
) else (
    echo ❌ Club Service is NOT running on port 8081
)
echo.

echo Checking Member Service (Port 8082)...
netstat -an | findstr ":8082" >nul
if %errorlevel% equ 0 (
    echo ✅ Member Service is running on port 8082
) else (
    echo ❌ Member Service is NOT running on port 8082
)
echo.

echo Checking Event Service (Port 8083)...
netstat -an | findstr ":8083" >nul
if %errorlevel% equ 0 (
    echo ✅ Event Service is running on port 8083
) else (
    echo ❌ Event Service is NOT running on port 8083
)
echo.

echo Checking Registration Service (Port 8084)...
netstat -an | findstr ":8084" >nul
if %errorlevel% equ 0 (
    echo ✅ Registration Service is running on port 8084
) else (
    echo ❌ Registration Service is NOT running on port 8084
)
echo.

echo ========================================
echo    Port Check Complete
echo ========================================
echo.
echo If any services are missing, run:
echo start-all-services-final.bat
echo.
pause
